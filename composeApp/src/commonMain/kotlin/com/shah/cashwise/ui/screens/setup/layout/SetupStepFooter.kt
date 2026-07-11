package com.shah.cashwise.ui.screens.setup.layout

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.shah.cashwise.ui.screens.setup.SetupAction
import com.shah.cashwise.ui.screens.setup.SetupState
import com.shah.cashwise.ui.screens.setup.steps.BudgetStepFooter
import com.shah.cashwise.ui.screens.setup.steps.InviteMembersFooter
import com.shah.cashwise.ui.screens.setup.steps.LockStepFooter

/**
 * Dispatches to the per-step pinned footer when [setupStepHasOwnFooter] is true
 * for the current step. The layouts call this in place of the default Continue
 * button — caller is responsible for the surrounding padding.
 */
@Composable
internal fun SetupStepFooter(
    state: SetupState,
    onAction: (SetupAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    when (state.currentStep) {
        2 -> LockStepFooter(onAction = onAction, modifier = modifier)
        3 -> BudgetStepFooter(
            submitEnabled = state.canSubmitBudget,
            saving = state.isSaving,
            onAction = onAction,
            modifier = modifier,
        )
        4 -> InviteMembersFooter(
            saving = state.isSaving,
            onAction = onAction,
            modifier = modifier,
        )
    }
}
