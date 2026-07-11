package com.shah.cashwise.ui.screens.setup.layout

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cashwise.composeapp.generated.resources.Res
import cashwise.composeapp.generated.resources.continue_action
import com.shah.cashwise.ui.components.PrimaryButton
import com.shah.cashwise.ui.screens.setup.SetupAction
import com.shah.cashwise.ui.screens.setup.SetupState
import com.shah.cashwise.ui.screens.setup.components.InviteMemberSheet
import org.jetbrains.compose.resources.stringResource

private val HorizontalGutter = 24.dp

/**
 * Stacked setup layout for phones (also reused, width-capped, for Medium and
 * Expanded). A fixed top bar, a scrollable header + step body, and a pinned
 * Continue button.
 */
@Composable
internal fun SetupCompactLayout(
    state: SetupState,
    onAction: (SetupAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    val (titleRes, descriptionRes) = setupStepHeader(state.currentStep)

    if (state.showInviteMember) {
        InviteMemberSheet(onDismiss = { onAction(SetupAction.InviteMemberDismissed) })
    }

    Column(modifier = modifier) {
        SetupTopBar(
            currentStep = state.currentStep,
            totalSteps = state.totalSteps,
            onBack = { onAction(SetupAction.Back) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = HorizontalGutter, vertical = 8.dp),
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = HorizontalGutter),
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            if (!setupStepHasOwnHeader(state.currentStep)) {
                SetupHeader(
                    title = titleRes,
                    description = descriptionRes,
                    modifier = Modifier.fillMaxWidth(),
                )

                Spacer(modifier = Modifier.height(28.dp))
            }

            SetupStepContent(
                state = state,
                onAction = onAction,
                useCenteredDialog = false,
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(modifier = Modifier.height(24.dp))
        }

        SetupSaveStatus(
            state = state,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = HorizontalGutter),
        )

        if (setupStepHasOwnFooter(state.currentStep)) {
            SetupStepFooter(
                state = state,
                onAction = onAction,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = HorizontalGutter)
                    .padding(top = 8.dp, bottom = 12.dp),
            )
        } else {
            PrimaryButton(
                text = stringResource(Res.string.continue_action),
                onClick = { onAction(SetupAction.Continue) },
                enabled = state.canContinue && !state.isSaving,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = HorizontalGutter)
                    .padding(top = 8.dp, bottom = 12.dp),
            )
        }
    }
}
