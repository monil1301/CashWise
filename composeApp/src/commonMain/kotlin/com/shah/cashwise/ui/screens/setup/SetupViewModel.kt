package com.shah.cashwise.ui.screens.setup

import androidx.lifecycle.ViewModel
import com.shah.cashwise.domain.model.AccountKind
import com.shah.cashwise.ui.screens.setup.model.CustomAccountDraft
import com.shah.cashwise.ui.screens.setup.model.SetupAccount
import com.shah.cashwise.ui.screens.setup.model.icon
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * Navigation outcome of a [SetupAction]. `null` from [SetupViewModel.onAction]
 * means the action was handled in place (state changed, no navigation).
 */
enum class SetupNavResult {
    /** Leave the setup flow (Back pressed on the first step). */
    ExitToWelcome,

    /** The whole setup flow is complete (Continue on the last step). */
    Finished,
}

/** Holds [SetupState] and drives step navigation for the setup flow. */
class SetupViewModel : ViewModel() {

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
                _state.update { it.copy(appLockEnabled = false) }
                handleContinue()
            }

            SetupAction.OpenSetPin -> {
                _state.update { it.copy(showSetPin = true) }
                null
            }

            is SetupAction.SetPinConfirmed -> {
                // TODO(persistence): store action.pin once a PIN/secure store exists.
                _state.update { it.copy(showSetPin = false) }
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

            SetupAction.BudgetSet -> {
                // TODO(persistence): write category + limit to a budgets store.
                handleContinue()
            }

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
        if (!current.canContinue) return null
        if (current.isLastStep) return SetupNavResult.Finished

        _state.update { it.copy(currentStep = it.currentStep + 1) }
        return null
    }
}
