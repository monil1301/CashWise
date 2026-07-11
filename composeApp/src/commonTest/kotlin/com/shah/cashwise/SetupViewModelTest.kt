package com.shah.cashwise

import com.shah.cashwise.domain.model.AccountKind
import com.shah.cashwise.domain.model.BudgetCategory
import com.shah.cashwise.domain.model.SupportedCurrencies
import com.shah.cashwise.domain.model.Wallet
import com.shah.cashwise.domain.model.WalletType
import com.shah.cashwise.domain.repo.AppLockRepository
import com.shah.cashwise.domain.repo.AppPreferencesRepository
import com.shah.cashwise.domain.repo.WalletRepository
import com.shah.cashwise.ui.screens.setup.SHARED_WALLET_SETUP_STEP_COUNT
import com.shah.cashwise.ui.screens.setup.SOLO_WALLET_SETUP_STEP_COUNT
import com.shah.cashwise.ui.screens.setup.SetupAction
import com.shah.cashwise.ui.screens.setup.SetupNavResult
import com.shah.cashwise.ui.screens.setup.SetupViewModel
import com.shah.cashwise.ui.screens.setup.model.CustomAccountDraft
import com.shah.cashwise.domain.model.CustomAccountIcon
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

private class FakeWalletRepository : WalletRepository {
    var result: Result<Unit> = Result.success(Unit)
    var saved: Wallet? = null
    var writeCount: Int = 0

    private val walletExists = MutableStateFlow(false)

    /** Mirrors what production derives setup-completion from. */
    val hasWalletNow: Boolean get() = walletExists.value

    override val wallets: Flow<List<Wallet>> = flowOf(emptyList())

    /** Setup completion is derived from this, exactly as in production. */
    override val hasWallet: Flow<Boolean> = walletExists

    override suspend fun createWallet(wallet: Wallet): Result<Unit> {
        writeCount++
        if (result.isSuccess) {
            saved = wallet
            walletExists.value = true
        }
        return result
    }
}

private class FakeAppLockRepository : AppLockRepository {
    private val enabled = MutableStateFlow(false)
    var storedPin: String? = null
    var cleared = false

    override val isLockEnabled: Flow<Boolean> = enabled
    override suspend fun setPin(pin: String): Result<Unit> {
        storedPin = pin
        enabled.value = true
        return Result.success(Unit)
    }
    override suspend fun verifyPin(pin: String): Boolean = pin == storedPin
    override suspend fun clearPin() {
        storedPin = null
        cleared = true
        enabled.value = false
    }
}

private class FakeAppPreferencesRepository : AppPreferencesRepository {
    val onboarding = MutableStateFlow(false)
    val appLock = MutableStateFlow(false)

    override val onboardingCompleted: Flow<Boolean> = onboarding
    override suspend fun setOnboardingCompleted(completed: Boolean) {
        onboarding.value = completed
    }

    override val appLockEnabled: Flow<Boolean> = appLock
    override suspend fun setAppLockEnabled(enabled: Boolean) {
        appLock.value = enabled
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
class SetupViewModelTest {

    private val dispatcher = StandardTestDispatcher()
    private val wallets = FakeWalletRepository()
    private val prefs = FakeAppPreferencesRepository()
    private val appLock = FakeAppLockRepository()

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(dispatcher)
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun viewModel() = SetupViewModel(wallets, prefs, appLock)

    // ---------------------------------------------------------------- navigation

    @Test
    fun backOnFirstStepExitsToWelcome() {
        assertEquals(SetupNavResult.ExitToWelcome, viewModel().onAction(SetupAction.Back))
    }

    @Test
    fun continueIsBlockedUntilWalletNameEntered() {
        val viewModel = viewModel()

        assertFalse(viewModel.state.value.canContinue)
        assertNull(viewModel.onAction(SetupAction.Continue))
        assertEquals(0, viewModel.state.value.currentStep)

        viewModel.onAction(SetupAction.WalletNameChanged("Personal"))
        assertTrue(viewModel.state.value.canContinue)
        assertNull(viewModel.onAction(SetupAction.Continue))
        assertEquals(1, viewModel.state.value.currentStep)
    }

    @Test
    fun backFromLaterStepDecrements() {
        val viewModel = viewModel()
        viewModel.onAction(SetupAction.WalletNameChanged("Personal"))
        viewModel.onAction(SetupAction.Continue)

        assertEquals(1, viewModel.state.value.currentStep)
        assertNull(viewModel.onAction(SetupAction.Back))
        assertEquals(0, viewModel.state.value.currentStep)
    }

    @Test
    fun soloWalletHasFourStepsByDefault() {
        val viewModel = viewModel()
        assertEquals(WalletType.Solo, viewModel.state.value.walletType)
        assertEquals(SOLO_WALLET_SETUP_STEP_COUNT, viewModel.state.value.totalSteps)
    }

    @Test
    fun sharedWalletExtendsToFiveSteps() {
        val viewModel = viewModel()

        viewModel.onAction(SetupAction.WalletTypeChanged(WalletType.Shared))
        assertEquals(SHARED_WALLET_SETUP_STEP_COUNT, viewModel.state.value.totalSteps)

        viewModel.onAction(SetupAction.WalletTypeChanged(WalletType.Solo))
        assertEquals(SOLO_WALLET_SETUP_STEP_COUNT, viewModel.state.value.totalSteps)
    }

    // ------------------------------------------------------------------ accounts

    @Test
    fun accountToggleAndBalanceChangesAreApplied() {
        val viewModel = viewModel()

        viewModel.onAction(SetupAction.AccountToggled("card", enabled = true))
        viewModel.onAction(SetupAction.AccountBalanceChanged("cash", "500"))

        val accounts = viewModel.state.value.accounts
        assertTrue(accounts.first { it.id == "card" }.enabled)
        assertEquals("500", accounts.first { it.id == "cash" }.startingBalance)
    }

    @Test
    fun addCustomAccountAppendsEnabledAccount() {
        val viewModel = viewModel()
        val initialCount = viewModel.state.value.accounts.size

        viewModel.onAction(
            SetupAction.CustomAccountAdded(
                CustomAccountDraft(
                    name = "Paytm",
                    icon = CustomAccountIcon.Wallet,
                    startingBalance = "1200",
                ),
            ),
        )

        val accounts = viewModel.state.value.accounts
        assertEquals(initialCount + 1, accounts.size)
        assertEquals(AccountKind.Custom, accounts.last().kind)
        assertTrue(accounts.last().enabled)
        assertEquals("Paytm", accounts.last().customName)
        assertEquals(CustomAccountIcon.Wallet, accounts.last().customIcon)
        assertEquals("1200", accounts.last().startingBalance)
    }

    @Test
    fun secondStepContinueRequiresAnEnabledAccount() {
        val viewModel = viewModel()
        viewModel.onAction(SetupAction.WalletNameChanged("Personal"))
        viewModel.onAction(SetupAction.Continue)
        assertEquals(1, viewModel.state.value.currentStep)

        viewModel.state.value.accounts.forEach { account ->
            viewModel.onAction(SetupAction.AccountToggled(account.id, enabled = false))
        }
        assertFalse(viewModel.state.value.canContinue)

        viewModel.onAction(SetupAction.AccountToggled("cash", enabled = true))
        assertTrue(viewModel.state.value.canContinue)
    }

    @Test
    fun walletTypeAndCurrencyChangesAreApplied() {
        val viewModel = viewModel()

        viewModel.onAction(SetupAction.WalletTypeChanged(WalletType.Shared))
        assertEquals(WalletType.Shared, viewModel.state.value.walletType)

        val usd = SupportedCurrencies.first { it.code == "USD" }
        viewModel.onAction(SetupAction.CurrencySelected(usd))
        assertEquals(usd, viewModel.state.value.selectedCurrency)
    }

    // ---------------------------------------------------------------- app lock

    @Test
    fun appLockToggleUpdatesState() {
        val viewModel = viewModel()
        assertTrue(viewModel.state.value.appLockEnabled)

        viewModel.onAction(SetupAction.AppLockToggled(enabled = false))
        assertFalse(viewModel.state.value.appLockEnabled)
    }

    @Test
    fun appLockSkippedDisablesLockAndAdvancesOneStep() {
        val viewModel = viewModel()

        viewModel.onAction(SetupAction.WalletNameChanged("Personal"))
        viewModel.onAction(SetupAction.Continue)
        viewModel.onAction(SetupAction.Continue)
        assertEquals(2, viewModel.state.value.currentStep)

        assertNull(viewModel.onAction(SetupAction.AppLockSkipped))
        assertFalse(viewModel.state.value.appLockEnabled)
        assertEquals(3, viewModel.state.value.currentStep)
    }

    @Test
    fun openSetPinRoutesIntoTheSubFlowWithoutAdvancing() {
        val viewModel = viewModel()
        viewModel.onAction(SetupAction.WalletNameChanged("Personal"))
        viewModel.onAction(SetupAction.Continue)
        viewModel.onAction(SetupAction.Continue)
        assertEquals(2, viewModel.state.value.currentStep)

        assertNull(viewModel.onAction(SetupAction.OpenSetPin))

        assertTrue(viewModel.state.value.showSetPin)
        assertEquals(2, viewModel.state.value.currentStep)
    }

    @Test
    fun setPinConfirmedClearsSubFlowAndAdvances() {
        val viewModel = viewModel()

        viewModel.onAction(SetupAction.WalletNameChanged("Personal"))
        viewModel.onAction(SetupAction.Continue)
        viewModel.onAction(SetupAction.Continue)
        viewModel.onAction(SetupAction.OpenSetPin)

        assertNull(viewModel.onAction(SetupAction.SetPinConfirmed("123456")))

        val state = viewModel.state.value
        assertFalse(state.showSetPin)
        assertTrue(state.appLockEnabled)
        assertEquals(3, state.currentStep)
    }

    @Test
    fun confirmingAPinStoresAVerifierForIt() = runTest(dispatcher) {
        val viewModel = viewModel()
        viewModel.onAction(SetupAction.WalletNameChanged("Personal"))
        viewModel.onAction(SetupAction.Continue)
        viewModel.onAction(SetupAction.Continue)
        viewModel.onAction(SetupAction.OpenSetPin)

        viewModel.onAction(SetupAction.SetPinConfirmed("123456"))
        advanceUntilIdle()

        assertEquals("123456", appLock.storedPin, "the PIN must reach the app-lock store")
        assertTrue(viewModel.state.value.appLockEnabled)
    }

    /** "Not now" must actually mean no lock — including after a PIN was set earlier in the run. */
    @Test
    fun skippingTheLockStepClearsAnyPinSetEarlier() = runTest(dispatcher) {
        val viewModel = viewModel()
        viewModel.onAction(SetupAction.WalletNameChanged("Personal"))
        viewModel.onAction(SetupAction.Continue)
        viewModel.onAction(SetupAction.Continue)
        viewModel.onAction(SetupAction.OpenSetPin)
        viewModel.onAction(SetupAction.SetPinConfirmed("123456"))
        advanceUntilIdle()
        assertEquals("123456", appLock.storedPin)

        // Go back to the lock step and skip it instead.
        viewModel.onAction(SetupAction.Back)
        viewModel.onAction(SetupAction.AppLockSkipped)
        advanceUntilIdle()

        assertNull(appLock.storedPin, "skipping the lock must remove the stored PIN")
        assertTrue(appLock.cleared)
        assertFalse(viewModel.state.value.appLockEnabled)
    }

    @Test
    fun setPinDismissedClearsSubFlowAndStaysOnLock() {
        val viewModel = viewModel()
        viewModel.onAction(SetupAction.WalletNameChanged("Personal"))
        viewModel.onAction(SetupAction.Continue)
        viewModel.onAction(SetupAction.Continue)
        viewModel.onAction(SetupAction.OpenSetPin)
        assertTrue(viewModel.state.value.showSetPin)

        assertNull(viewModel.onAction(SetupAction.SetPinDismissed))

        val state = viewModel.state.value
        assertFalse(state.showSetPin)
        assertEquals(2, state.currentStep)
    }

    // ------------------------------------------------------------------- budget

    @Test
    fun budgetCategoryAndLimitChangesAreApplied() {
        val viewModel = viewModel()

        viewModel.onAction(SetupAction.BudgetCategorySelected(BudgetCategory.Transport))
        assertEquals(BudgetCategory.Transport, viewModel.state.value.selectedBudgetCategory)

        viewModel.onAction(SetupAction.BudgetLimitChanged("2500"))
        assertEquals("2500", viewModel.state.value.budgetLimit)
    }

    @Test
    fun canSubmitBudgetReflectsLimitInput() {
        val viewModel = viewModel()
        advanceSoloToBudgetStep(viewModel)
        assertFalse(viewModel.state.value.canSubmitBudget) // default ""

        viewModel.onAction(SetupAction.BudgetLimitChanged("0"))
        assertFalse(viewModel.state.value.canSubmitBudget)

        viewModel.onAction(SetupAction.BudgetLimitChanged("abc"))
        assertFalse(viewModel.state.value.canSubmitBudget)

        viewModel.onAction(SetupAction.BudgetLimitChanged("  500 "))
        assertTrue(viewModel.state.value.canSubmitBudget)

        viewModel.onAction(SetupAction.BudgetLimitChanged("0.5"))
        assertTrue(viewModel.state.value.canSubmitBudget)
    }

    @Test
    fun budgetSetAdvancesToInviteStepForSharedWallet() {
        val viewModel = viewModel()
        viewModel.onAction(SetupAction.WalletTypeChanged(WalletType.Shared))
        advanceSoloToBudgetStep(viewModel)

        viewModel.onAction(SetupAction.BudgetLimitChanged("4000"))
        val result = viewModel.onAction(SetupAction.BudgetSet)

        assertNull(result)
        assertEquals(4, viewModel.state.value.currentStep)
    }

    // -------------------------------------------------------------- invite sheet

    @Test
    fun inviteViaLinkOpensInviteMemberSheet() {
        val viewModel = viewModel()
        viewModel.onAction(SetupAction.WalletTypeChanged(WalletType.Shared))
        advanceSoloToBudgetStep(viewModel)
        viewModel.onAction(SetupAction.BudgetSet)
        assertEquals(4, viewModel.state.value.currentStep)
        assertFalse(viewModel.state.value.showInviteMember)

        assertNull(viewModel.onAction(SetupAction.InviteViaLinkClicked))

        assertTrue(viewModel.state.value.showInviteMember)
        assertEquals(4, viewModel.state.value.currentStep)
    }

    @Test
    fun showQrCodeOpensInviteMemberSheet() {
        val viewModel = viewModel()
        viewModel.onAction(SetupAction.WalletTypeChanged(WalletType.Shared))
        advanceSoloToBudgetStep(viewModel)
        viewModel.onAction(SetupAction.BudgetSet)
        assertFalse(viewModel.state.value.showInviteMember)

        assertNull(viewModel.onAction(SetupAction.ShowQrCodeClicked))

        assertTrue(viewModel.state.value.showInviteMember)
    }

    @Test
    fun inviteMemberDismissedClosesSheet() {
        val viewModel = viewModel()
        viewModel.onAction(SetupAction.WalletTypeChanged(WalletType.Shared))
        advanceSoloToBudgetStep(viewModel)
        viewModel.onAction(SetupAction.BudgetSet)
        viewModel.onAction(SetupAction.ShowQrCodeClicked)
        assertTrue(viewModel.state.value.showInviteMember)

        assertNull(viewModel.onAction(SetupAction.InviteMemberDismissed))

        assertFalse(viewModel.state.value.showInviteMember)
        assertEquals(4, viewModel.state.value.currentStep)
    }

    // -------------------------------------------------------------- persistence
    //
    // The whole point of the setup flow: what the user entered must survive a
    // relaunch. Finishing writes the wallet, then flips the persisted
    // `setupCompleted` flag — which is what navigates the user onward.

    @Test
    fun finishingSoloSetupPersistsWalletAndMarksSetupCompleted() = runTest(dispatcher) {
        val viewModel = viewModel()
        viewModel.onAction(SetupAction.WalletNameChanged("  Household  "))
        advanceSoloToBudgetStep(viewModel)

        viewModel.onAction(SetupAction.BudgetSkipped)
        advanceUntilIdle()

        val saved = assertNotNull(wallets.saved, "the wallet should have been written")
        assertEquals("Household", saved.name, "name is trimmed before storing")
        assertEquals(WalletType.Solo, saved.type)
        // Only accounts left switched on are persisted — Card is off by default.
        assertEquals(
            listOf(AccountKind.Cash, AccountKind.Upi, AccountKind.Bank),
            saved.accounts.map { it.kind },
        )
        // Completion is derived from the wallet existing — no separate flag to disagree with it.
        assertTrue(wallets.hasWalletNow, "the stored wallet is what marks setup complete")
        assertFalse(viewModel.state.value.saveFailed)
        // isSaving deliberately stays true: navigation away is async, and re-enabling the
        // button in the meantime would let a second tap write a second wallet.
        assertTrue(viewModel.state.value.isSaving)
    }

    @Test
    fun finishingSharedSetupFromInviteStepPersists() = runTest(dispatcher) {
        val viewModel = viewModel()
        viewModel.onAction(SetupAction.WalletTypeChanged(WalletType.Shared))
        advanceSoloToBudgetStep(viewModel)
        viewModel.onAction(SetupAction.BudgetSet)
        assertTrue(viewModel.state.value.isLastStep)

        viewModel.onAction(SetupAction.InviteMembersDone)
        advanceUntilIdle()

        assertEquals(WalletType.Shared, wallets.saved?.type)
        assertTrue(wallets.hasWalletNow)
    }

    @Test
    fun budgetLimitIsStoredInMinorUnits() = runTest(dispatcher) {
        val viewModel = viewModel()
        advanceSoloToBudgetStep(viewModel)

        viewModel.onAction(SetupAction.BudgetCategorySelected(BudgetCategory.Transport))
        viewModel.onAction(SetupAction.BudgetLimitChanged("1200.50"))
        viewModel.onAction(SetupAction.BudgetSet)
        advanceUntilIdle()

        val budget = assertNotNull(wallets.saved?.budgets?.singleOrNull())
        assertEquals(120050L, budget.limitMinor, "money is stored as minor units, never a float")
        assertEquals(BudgetCategory.Transport, budget.category)
    }

    @Test
    fun startingBalanceIsStoredInMinorUnits() = runTest(dispatcher) {
        val viewModel = viewModel()
        viewModel.onAction(SetupAction.AccountBalanceChanged("cash", "500.25"))
        advanceSoloToBudgetStep(viewModel)

        viewModel.onAction(SetupAction.BudgetSkipped)
        advanceUntilIdle()

        val cash = assertNotNull(wallets.saved?.accounts?.firstOrNull { it.kind == AccountKind.Cash })
        assertEquals(50025L, cash.startingBalanceMinor)
    }

    @Test
    fun skippingBudgetStoresNoBudget() = runTest(dispatcher) {
        val viewModel = viewModel()
        advanceSoloToBudgetStep(viewModel)
        viewModel.onAction(SetupAction.BudgetLimitChanged("4000"))

        viewModel.onAction(SetupAction.BudgetSkipped)
        advanceUntilIdle()

        assertTrue(wallets.saved?.budgets.orEmpty().isEmpty(), "skipping must not store a budget")
    }

    @Test
    fun selectedCurrencyIsPersistedWithTheWallet() = runTest(dispatcher) {
        val viewModel = viewModel()
        viewModel.onAction(SetupAction.CurrencySelected(SupportedCurrencies.first { it.code == "USD" }))
        advanceSoloToBudgetStep(viewModel)

        viewModel.onAction(SetupAction.BudgetSkipped)
        advanceUntilIdle()

        assertEquals("USD", wallets.saved?.currency?.code)
    }

    @Test
    fun appLockChoiceIsPersisted() = runTest(dispatcher) {
        val viewModel = viewModel()
        advanceSoloToBudgetStep(viewModel) // AppLockSkipped -> disabled

        viewModel.onAction(SetupAction.BudgetSkipped)
        advanceUntilIdle()

        assertFalse(prefs.appLock.value, "skipping the lock step must persist as disabled")
    }

    /**
     * The persisted flag is what navigates the user away from setup, so it must not
     * be set when the write failed — otherwise setup is skipped forever with no wallet.
     */
    @Test
    fun failedWriteKeepsUserOnSetupAndSurfacesError() = runTest(dispatcher) {
        wallets.result = Result.failure(RuntimeException("disk full"))
        val viewModel = viewModel()
        advanceSoloToBudgetStep(viewModel)

        viewModel.onAction(SetupAction.BudgetSkipped)
        advanceUntilIdle()

        assertFalse(wallets.hasWalletNow, "a failed save must never mark setup complete")
        assertTrue(viewModel.state.value.saveFailed)
        // Re-enabled so the user can retry.
        assertFalse(viewModel.state.value.isSaving)
    }

    @Test
    fun continueIsIgnoredWhileSavingSoTheWalletIsNotWrittenTwice() = runTest(dispatcher) {
        val viewModel = viewModel()
        advanceSoloToBudgetStep(viewModel)

        viewModel.onAction(SetupAction.BudgetSkipped)
        assertTrue(viewModel.state.value.isSaving, "save is in flight")
        viewModel.onAction(SetupAction.Continue) // must be a no-op
        advanceUntilIdle()

        assertEquals(1, wallets.writeCount, "the wallet must be written exactly once")
    }

    private fun advanceSoloToBudgetStep(viewModel: SetupViewModel) {
        if (viewModel.state.value.walletName.isBlank()) {
            viewModel.onAction(SetupAction.WalletNameChanged("Personal"))
        }
        viewModel.onAction(SetupAction.Continue) // step 0 -> 1
        viewModel.onAction(SetupAction.Continue) // step 1 -> 2 (Lock)
        viewModel.onAction(SetupAction.AppLockSkipped) // step 2 -> 3 (Budget)
        assertEquals(3, viewModel.state.value.currentStep)
    }
}
