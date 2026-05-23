package com.shah.cashwise.ui.screens.setup

import com.shah.cashwise.domain.model.BudgetCategory
import com.shah.cashwise.domain.model.Currency
import com.shah.cashwise.domain.model.DefaultCurrency
import com.shah.cashwise.domain.model.WalletType
import com.shah.cashwise.ui.screens.setup.model.SetupAccount
import com.shah.cashwise.ui.screens.setup.model.defaultSetupAccounts

/**
 * State for the multi-step [SetupScreen]. [currentStep] is zero-based;
 * [totalSteps] is derived from [walletType] — [WalletType.Shared] adds the
 * "Invite members" step at the end of the flow. The wallet fields back the
 * first step, "Create your first wallet"; [appLockEnabled] backs the third
 * step, "Lock Cashwise".
 */
data class SetupState(
    val currentStep: Int = 0,
    val walletName: String = "",
    val walletType: WalletType = WalletType.Solo,
    val selectedCurrency: Currency = DefaultCurrency,
    val accounts: List<SetupAccount> = defaultSetupAccounts(),
    val appLockEnabled: Boolean = true,
    /**
     * When true the setup flow hands off to [SetPinScreen] as a sub-route — the
     * Set PIN screen is not a counted step (it has no step indicator). Toggled
     * by the Lock step's "Set PIN" button and the Set PIN screen's exit /
     * confirm callbacks.
     */
    val showSetPin: Boolean = false,
    val selectedBudgetCategory: BudgetCategory = BudgetCategory.FoodAndDining,
    val budgetLimit: String = "",
    /**
     * When true the "Invite a member" sheet/dialog is presented as a modal
     * overlay over the current step (typically the Invite Members step on a
     * Shared wallet). Toggled by `InviteViaLinkClicked` / `ShowQrCodeClicked`
     * and `InviteMemberDismissed`.
     */
    val showInviteMember: Boolean = false,
) {
    /**
     * Total step count for the current [walletType]. Solo wallets have four
     * steps; Shared wallets gain a fifth "Invite members" step at the end.
     */
    val totalSteps: Int
        get() = when (walletType) {
            WalletType.Solo -> SOLO_WALLET_SETUP_STEP_COUNT
            WalletType.Shared -> SHARED_WALLET_SETUP_STEP_COUNT
        }

    /** True when the user is on the final step. */
    val isLastStep: Boolean
        get() = totalSteps > 0 && currentStep == totalSteps - 1

    /**
     * Whether the Continue button is enabled. Step 1 requires a non-blank
     * wallet name; step 2 requires at least one enabled account; later steps
     * have no input gate yet.
     */
    val canContinue: Boolean
        get() = when (currentStep) {
            0 -> walletName.isNotBlank()
            1 -> accounts.any { it.enabled }
            else -> true
        }

    /**
     * Whether "Set budget" is enabled on the budget step — true only when
     * [budgetLimit] parses to a number greater than zero. "Skip for now" is
     * always available regardless.
     */
    val canSubmitBudget: Boolean
        get() = (budgetLimit.trim().toDoubleOrNull() ?: 0.0) > 0.0
}
