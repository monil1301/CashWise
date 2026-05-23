package com.shah.cashwise.ui.screens.setup.layout

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cashwise.composeapp.generated.resources.Res
import cashwise.composeapp.generated.resources.setup_add_accounts_description
import cashwise.composeapp.generated.resources.setup_add_accounts_title
import cashwise.composeapp.generated.resources.setup_budget_description
import cashwise.composeapp.generated.resources.setup_budget_title
import cashwise.composeapp.generated.resources.setup_create_wallet_description
import cashwise.composeapp.generated.resources.setup_create_wallet_title
import cashwise.composeapp.generated.resources.setup_invite_description
import cashwise.composeapp.generated.resources.setup_invite_title
import cashwise.composeapp.generated.resources.setup_step_placeholder_description
import cashwise.composeapp.generated.resources.setup_step_placeholder_title
import com.shah.cashwise.ui.screens.setup.SetupAction
import com.shah.cashwise.ui.screens.setup.SetupState
import com.shah.cashwise.ui.screens.setup.steps.AddAccountsStep
import com.shah.cashwise.ui.screens.setup.steps.CreateWalletStep
import com.shah.cashwise.ui.screens.setup.steps.InviteMembersStep
import com.shah.cashwise.ui.screens.setup.steps.LockCashwiseStep
import com.shah.cashwise.ui.screens.setup.steps.SetBudgetStep
import org.jetbrains.compose.resources.StringResource

/**
 * Title/description string pair for the [SetupHeader] of the given step.
 * Step 2 renders its own header (see [setupStepHasOwnHeader]) so it has no
 * entry here.
 */
internal fun setupStepHeader(currentStep: Int): Pair<StringResource, StringResource> =
    when (currentStep) {
        0 -> Res.string.setup_create_wallet_title to Res.string.setup_create_wallet_description
        1 -> Res.string.setup_add_accounts_title to Res.string.setup_add_accounts_description
        3 -> Res.string.setup_budget_title to Res.string.setup_budget_description
        4 -> Res.string.setup_invite_title to Res.string.setup_invite_description
        else -> Res.string.setup_step_placeholder_title to Res.string.setup_step_placeholder_description
    }

/**
 * Whether the step renders its own title block instead of the shared
 * [SetupHeader] — true for the "Lock Cashwise" step, whose centred icon sits
 * above a left-aligned title.
 */
internal fun setupStepHasOwnHeader(currentStep: Int): Boolean = currentStep == 2

/**
 * Whether the step renders its own footer actions instead of the layout's
 * pinned Continue button — true for "Lock Cashwise" ("Set PIN" / "Not now"),
 * "Set your first budget" ("Set budget" / "Skip for now"), and "Invite members"
 * ("Done" + skip caption). See [SetupStepFooter] for the dispatch.
 */
internal fun setupStepHasOwnFooter(currentStep: Int): Boolean =
    currentStep == 2 || currentStep == 3 || currentStep == 4

/**
 * Step-specific body below the common [SetupHeader].
 *
 * [useCenteredDialog] is forwarded to [AddAccountsStep] so wide layouts present
 * the add-custom-account form as a centered dialog rather than a bottom sheet.
 */
@Composable
internal fun SetupStepContent(
    state: SetupState,
    onAction: (SetupAction) -> Unit,
    useCenteredDialog: Boolean,
    modifier: Modifier = Modifier,
) {
    when (state.currentStep) {
        0 -> CreateWalletStep(
            walletName = state.walletName,
            walletType = state.walletType,
            selectedCurrency = state.selectedCurrency,
            onAction = onAction,
            modifier = modifier,
        )

        1 -> AddAccountsStep(
            accounts = state.accounts,
            onAction = onAction,
            useCenteredDialog = useCenteredDialog,
            modifier = modifier,
        )

        2 -> LockCashwiseStep(
            appLockEnabled = state.appLockEnabled,
            onAction = onAction,
            modifier = modifier,
        )

        3 -> SetBudgetStep(
            selectedCategory = state.selectedBudgetCategory,
            monthlyLimit = state.budgetLimit,
            onAction = onAction,
            modifier = modifier,
        )

        4 -> InviteMembersStep(
            onAction = onAction,
            modifier = modifier,
        )

        else -> Unit
    }
}
