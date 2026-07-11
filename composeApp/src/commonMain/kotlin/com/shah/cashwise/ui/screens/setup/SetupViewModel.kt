package com.shah.cashwise.ui.screens.setup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shah.cashwise.core.extensions.toMinorUnits
import com.shah.cashwise.domain.model.Account
import com.shah.cashwise.domain.model.AccountKind
import com.shah.cashwise.domain.model.Budget
import com.shah.cashwise.domain.model.Wallet
import com.shah.cashwise.domain.repo.AppLockRepository
import com.shah.cashwise.domain.repo.AppPreferencesRepository
import com.shah.cashwise.domain.repo.WalletRepository
import com.shah.cashwise.ui.screens.setup.model.CustomAccountDraft
import com.shah.cashwise.ui.screens.setup.model.SetupAccount
import com.shah.cashwise.ui.screens.setup.model.icon
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Clock
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

/**
 * Navigation outcome of a [SetupAction]. `null` from [SetupViewModel.onAction]
 * means the action was handled in place (state changed, no navigation).
 *
 * There is deliberately no `Finished` result: completing setup writes the wallet
 * and flips the persisted `setupCompleted` flag, and `AppNavigation` moves on by
 * observing that flag. Keeping one source of truth means the screen can never
 * navigate away from a setup that failed to save.
 */
enum class SetupNavResult {
    /** Leave the setup flow (Back pressed on the first step). */
    ExitToWelcome,
}

/**
 * Holds [SetupState], drives step navigation, and persists the finished setup.
 */
@OptIn(ExperimentalUuidApi::class)
class SetupViewModel(
    private val walletRepository: WalletRepository,
    private val appPreferencesRepository: AppPreferencesRepository,
    private val appLockRepository: AppLockRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(SetupState())
    val state: StateFlow<SetupState> = _state.asStateFlow()

    fun onAction(action: SetupAction): SetupNavResult? {
        return when (action) {
            SetupAction.Back -> handleBack()
            SetupAction.Continue -> handleContinue()
            is SetupAction.WalletNameChanged -> {
                _state.update { it.copy(walletName = action.name) }
                null
            }

            is SetupAction.WalletTypeChanged -> {
                _state.update { it.copy(walletType = action.type) }
                null
            }

            is SetupAction.CurrencySelected -> {
                _state.update { it.copy(selectedCurrency = action.currency) }
                null
            }

            is SetupAction.AccountToggled -> {
                updateAccount(action.accountId) { it.copy(enabled = action.enabled) }
                null
            }

            is SetupAction.AccountBalanceChanged -> {
                updateAccount(action.accountId) { it.copy(startingBalance = action.balance) }
                null
            }

            is SetupAction.CustomAccountAdded -> {
                addCustomAccount(action.draft)
                null
            }

            is SetupAction.AppLockToggled -> {
                _state.update { it.copy(appLockEnabled = action.enabled) }
                null
            }

            SetupAction.AppLockSkipped -> {
                // Clear any PIN set earlier in this run — "Not now" must actually mean no lock.
                viewModelScope.launch { appLockRepository.clearPin() }
                _state.update { it.copy(appLockEnabled = false) }
                handleContinue()
            }

            SetupAction.OpenSetPin -> {
                _state.update { it.copy(showSetPin = true) }
                null
            }

            is SetupAction.SetPinConfirmed -> {
                // Store a verifier for the PIN (never the PIN). Deriving it is deliberately
                // slow, so it happens off the main thread inside the repository.
                viewModelScope.launch { appLockRepository.setPin(action.pin) }
                _state.update { it.copy(appLockEnabled = true, showSetPin = false) }
                handleContinue()
            }

            SetupAction.SetPinDismissed -> {
                _state.update { it.copy(showSetPin = false) }
                null
            }

            is SetupAction.BudgetCategorySelected -> {
                _state.update { it.copy(selectedBudgetCategory = action.category) }
                null
            }

            is SetupAction.BudgetLimitChanged -> {
                _state.update { it.copy(budgetLimit = action.limit) }
                null
            }

            // The chosen category/limit already live in state and are written by
            // persistSetup() when the flow finishes; skipping clears the limit.
            SetupAction.BudgetSet -> handleContinue()

            SetupAction.BudgetSkipped -> {
                _state.update { it.copy(budgetLimit = "") }
                handleContinue()
            }

            SetupAction.InviteViaLinkClicked -> {
                _state.update { it.copy(showInviteMember = true) }
                null
            }

            SetupAction.ShowQrCodeClicked -> {
                _state.update { it.copy(showInviteMember = true) }
                null
            }

            SetupAction.InviteMemberDismissed -> {
                _state.update { it.copy(showInviteMember = false) }
                null
            }

            SetupAction.InviteMembersDone -> handleContinue()
        }
    }

    private fun updateAccount(accountId: String, transform: (SetupAccount) -> SetupAccount) {
        _state.update { state ->
            state.copy(
                accounts = state.accounts.map { account ->
                    if (account.id == accountId) transform(account) else account
                },
            )
        }
    }

    private fun addCustomAccount(draft: CustomAccountDraft) {
        _state.update { state ->
            val customCount = state.accounts.count { it.kind == AccountKind.Custom }
            val newAccount = SetupAccount(
                id = "custom-${customCount + 1}",
                kind = AccountKind.Custom,
                enabled = true,
                startingBalance = draft.startingBalance,
                customName = draft.name,
                customIcon = draft.icon,
            )
            state.copy(accounts = state.accounts + newAccount)
        }
    }

    private fun handleBack(): SetupNavResult? {
        val current = _state.value
        if (current.currentStep == 0) return SetupNavResult.ExitToWelcome

        _state.update { it.copy(currentStep = it.currentStep - 1) }
        return null
    }

    private fun handleContinue(): SetupNavResult? {
        val current = _state.value
        if (!current.canContinue || current.isSaving) return null
        if (current.isLastStep) {
            persistSetup()
            return null
        }

        _state.update { it.copy(currentStep = it.currentStep + 1) }
        return null
    }

    /**
     * Writes the finished setup: the app-lock preference first, then the wallet with its
     * enabled accounts and optional budget in a single transaction.
     *
     * Ordering is deliberate. Storing the wallet is what marks setup complete (navigation
     * observes `WalletRepository.hasWallet`), so it is the *last* thing written and the
     * only decisive one. Nothing runs after it, which means there is no window in which
     * this coroutine can be cancelled — by the screen being torn down, or by process death
     * — between "wallet committed" and "some second store agreed it was committed". A
     * failure leaves nothing written and the user on the last step with their input intact.
     *
     * [SetupState.isSaving] is deliberately NOT cleared on success: navigation away is
     * asynchronous, and re-enabling the button in the meantime would let a second tap write
     * a second wallet.
     */
    private fun persistSetup() {
        val current = _state.value
        _state.update { it.copy(isSaving = true, saveFailed = false) }
        viewModelScope.launch {
            val result = runCatching {
                appPreferencesRepository.setAppLockEnabled(current.appLockEnabled)
            }.mapCatching {
                walletRepository.createWallet(buildWallet(current)).getOrThrow()
            }
            result.onFailure { error ->
                if (error is CancellationException) throw error
                _state.update { it.copy(isSaving = false, saveFailed = true) }
            }
            // Success: keep isSaving = true. The new wallet flips hasWallet, AppState
            // updates, and AppNavigation leaves this screen.
        }
    }

    /** Maps the collected [SetupState] into the domain [Wallet] aggregate to store. */
    private fun buildWallet(state: SetupState): Wallet = Wallet(
        id = Uuid.random().toString(),
        name = state.walletName.trim(),
        type = state.walletType,
        currency = state.selectedCurrency,
        createdAt = Clock.System.now().toEpochMilliseconds(),
        // Only accounts the user left switched on are real accounts.
        accounts = state.accounts.filter { it.enabled }.map { account ->
            Account(
                id = Uuid.random().toString(),
                kind = account.kind,
                startingBalanceMinor = account.startingBalance.toMinorUnits(),
                customName = account.customName.takeIf { account.kind == AccountKind.Custom },
                customIcon = account.customIcon.takeIf { account.kind == AccountKind.Custom },
            )
        },
        // The budget step is skippable, so a blank/zero limit means no budget.
        budgets = state.budgetLimit.toMinorUnits()
            .takeIf { it > 0L }
            ?.let { limitMinor ->
                listOf(
                    Budget(
                        id = Uuid.random().toString(),
                        category = state.selectedBudgetCategory,
                        limitMinor = limitMinor,
                    ),
                )
            }
            .orEmpty(),
    )
}
