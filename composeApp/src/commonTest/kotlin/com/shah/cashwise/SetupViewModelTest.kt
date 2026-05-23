package com.shah.cashwise

import com.shah.cashwise.domain.model.AccountKind
import com.shah.cashwise.domain.model.BudgetCategory
import com.shah.cashwise.domain.model.SupportedCurrencies
import com.shah.cashwise.domain.model.WalletType
import com.shah.cashwise.ui.screens.setup.SHARED_WALLET_SETUP_STEP_COUNT
import com.shah.cashwise.ui.screens.setup.SOLO_WALLET_SETUP_STEP_COUNT
import com.shah.cashwise.ui.screens.setup.SetupAction
import com.shah.cashwise.ui.screens.setup.SetupNavResult
import com.shah.cashwise.ui.screens.setup.SetupViewModel
import com.shah.cashwise.ui.screens.setup.model.CustomAccountDraft
import com.shah.cashwise.ui.screens.setup.model.CustomAccountIcon
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

class SetupViewModelTest {

    @Test
    fun backOnFirstStepExitsToWelcome() {
        val viewModel = SetupViewModel()
        assertEquals(SetupNavResult.ExitToWelcome, viewModel.onAction(SetupAction.Back))
    }

    @Test
    fun continueIsBlockedUntilWalletNameEntered() {
        val viewModel = SetupViewModel()

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
        val viewModel = SetupViewModel()
        viewModel.onAction(SetupAction.WalletNameChanged("Personal"))
        viewModel.onAction(SetupAction.Continue)

        assertEquals(1, viewModel.state.value.currentStep)
        assertNull(viewModel.onAction(SetupAction.Back))
        assertEquals(0, viewModel.state.value.currentStep)
    }

    @Test
    fun soloWalletHasFourStepsByDefault() {
        val viewModel = SetupViewModel()
        assertEquals(WalletType.Solo, viewModel.state.value.walletType)
        assertEquals(SOLO_WALLET_SETUP_STEP_COUNT, viewModel.state.value.totalSteps)
    }

    @Test
    fun sharedWalletExtendsToFiveSteps() {
        val viewModel = SetupViewModel()

        viewModel.onAction(SetupAction.WalletTypeChanged(WalletType.Shared))
        assertEquals(SHARED_WALLET_SETUP_STEP_COUNT, viewModel.state.value.totalSteps)

        viewModel.onAction(SetupAction.WalletTypeChanged(WalletType.Solo))
        assertEquals(SOLO_WALLET_SETUP_STEP_COUNT, viewModel.state.value.totalSteps)
    }

    @Test
    fun accountToggleAndBalanceChangesAreApplied() {
        val viewModel = SetupViewModel()

        viewModel.onAction(SetupAction.AccountToggled("card", enabled = true))
        viewModel.onAction(SetupAction.AccountBalanceChanged("cash", "500"))

        val accounts = viewModel.state.value.accounts
        assertTrue(accounts.first { it.id == "card" }.enabled)
        assertEquals("500", accounts.first { it.id == "cash" }.startingBalance)
    }

    @Test
    fun addCustomAccountAppendsEnabledAccount() {
        val viewModel = SetupViewModel()
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
        val viewModel = SetupViewModel()
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
        val viewModel = SetupViewModel()

        viewModel.onAction(SetupAction.WalletTypeChanged(WalletType.Shared))
        assertEquals(WalletType.Shared, viewModel.state.value.walletType)

        val usd = SupportedCurrencies.first { it.code == "USD" }
        viewModel.onAction(SetupAction.CurrencySelected(usd))
        assertEquals(usd, viewModel.state.value.selectedCurrency)
    }

    @Test
    fun appLockToggleUpdatesState() {
        val viewModel = SetupViewModel()
        assertTrue(viewModel.state.value.appLockEnabled)

        viewModel.onAction(SetupAction.AppLockToggled(enabled = false))
        assertFalse(viewModel.state.value.appLockEnabled)
    }

    @Test
    fun appLockSkippedDisablesLockAndAdvancesOneStep() {
        val viewModel = SetupViewModel()

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
        val viewModel = SetupViewModel()
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
        val viewModel = SetupViewModel()

        viewModel.onAction(SetupAction.WalletNameChanged("Personal"))
        viewModel.onAction(SetupAction.Continue)
        viewModel.onAction(SetupAction.Continue)
        viewModel.onAction(SetupAction.OpenSetPin)

        assertNull(viewModel.onAction(SetupAction.SetPinConfirmed("123456")))

        val state = viewModel.state.value
        assertFalse(state.showSetPin)
        assertEquals(3, state.currentStep)
    }

    @Test
    fun setPinDismissedClearsSubFlowAndStaysOnLock() {
        val viewModel = SetupViewModel()
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

    @Test
    fun budgetCategoryAndLimitChangesAreApplied() {
        val viewModel = SetupViewModel()

        viewModel.onAction(SetupAction.BudgetCategorySelected(BudgetCategory.Transport))
        assertEquals(BudgetCategory.Transport, viewModel.state.value.selectedBudgetCategory)

        viewModel.onAction(SetupAction.BudgetLimitChanged("2500"))
        assertEquals("2500", viewModel.state.value.budgetLimit)
    }

    @Test
    fun budgetSetFinishesSetupForSoloWallet() {
        val viewModel = SetupViewModel()
        advanceSoloToBudgetStep(viewModel)

        viewModel.onAction(SetupAction.BudgetLimitChanged("4000"))

        val result = viewModel.onAction(SetupAction.BudgetSet)

        assertEquals(SetupNavResult.Finished, result)
        assertEquals("4000", viewModel.state.value.budgetLimit)
    }

    @Test
    fun budgetSetAdvancesToInviteStepForSharedWallet() {
        val viewModel = SetupViewModel()
        viewModel.onAction(SetupAction.WalletTypeChanged(WalletType.Shared))
        advanceSoloToBudgetStep(viewModel)

        viewModel.onAction(SetupAction.BudgetLimitChanged("4000"))
        val result = viewModel.onAction(SetupAction.BudgetSet)

        assertNull(result)
        assertEquals(4, viewModel.state.value.currentStep)
    }

    @Test
    fun canSubmitBudgetReflectsLimitInput() {
        val viewModel = SetupViewModel()
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
    fun budgetSkippedFinishesSetupForSoloWallet() {
        val viewModel = SetupViewModel()
        advanceSoloToBudgetStep(viewModel)
        viewModel.onAction(SetupAction.BudgetLimitChanged("4000"))

        val result = viewModel.onAction(SetupAction.BudgetSkipped)

        assertEquals(SetupNavResult.Finished, result)
        assertEquals("", viewModel.state.value.budgetLimit)
    }

    @Test
    fun inviteMembersDoneFinishesSetupForSharedWallet() {
        val viewModel = SetupViewModel()
        viewModel.onAction(SetupAction.WalletTypeChanged(WalletType.Shared))
        advanceSoloToBudgetStep(viewModel)
        viewModel.onAction(SetupAction.BudgetSet)
        assertEquals(4, viewModel.state.value.currentStep)
        assertTrue(viewModel.state.value.isLastStep)

        val result = viewModel.onAction(SetupAction.InviteMembersDone)

        assertEquals(SetupNavResult.Finished, result)
    }

    @Test
    fun inviteViaLinkOpensInviteMemberSheet() {
        val viewModel = SetupViewModel()
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
        val viewModel = SetupViewModel()
        viewModel.onAction(SetupAction.WalletTypeChanged(WalletType.Shared))
        advanceSoloToBudgetStep(viewModel)
        viewModel.onAction(SetupAction.BudgetSet)
        assertFalse(viewModel.state.value.showInviteMember)

        assertNull(viewModel.onAction(SetupAction.ShowQrCodeClicked))

        assertTrue(viewModel.state.value.showInviteMember)
    }

    @Test
    fun inviteMemberDismissedClosesSheet() {
        val viewModel = SetupViewModel()
        viewModel.onAction(SetupAction.WalletTypeChanged(WalletType.Shared))
        advanceSoloToBudgetStep(viewModel)
        viewModel.onAction(SetupAction.BudgetSet)
        viewModel.onAction(SetupAction.ShowQrCodeClicked)
        assertTrue(viewModel.state.value.showInviteMember)

        assertNull(viewModel.onAction(SetupAction.InviteMemberDismissed))

        assertFalse(viewModel.state.value.showInviteMember)
        assertEquals(4, viewModel.state.value.currentStep)
    }

    private fun advanceSoloToBudgetStep(viewModel: SetupViewModel) {
        viewModel.onAction(SetupAction.WalletNameChanged("Personal"))
        viewModel.onAction(SetupAction.Continue) // step 0 -> 1
        viewModel.onAction(SetupAction.Continue) // step 1 -> 2 (Lock)
        viewModel.onAction(SetupAction.AppLockSkipped) // step 2 -> 3 (Budget)
        assertEquals(3, viewModel.state.value.currentStep)
    }
}
