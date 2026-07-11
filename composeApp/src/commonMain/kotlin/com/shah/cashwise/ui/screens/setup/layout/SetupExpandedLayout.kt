package com.shah.cashwise.ui.screens.setup.layout

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cashwise.composeapp.generated.resources.Res
import cashwise.composeapp.generated.resources.continue_action
import com.shah.cashwise.ui.components.PrimaryButton
import com.shah.cashwise.ui.screens.setup.SetupAction
import com.shah.cashwise.ui.screens.setup.SetupState
import com.shah.cashwise.ui.screens.setup.components.InviteMemberDialog
import org.jetbrains.compose.resources.stringResource

/**
 * Split setup layout for tablet-landscape / desktop: the brand panel with the
 * step illustration on the left, the step form on the right. The right side
 * scrolls its content with the Continue button pinned to the bottom. The
 * expanded layout has no back button (it mirrors the provided mock).
 */
@Composable
internal fun SetupExpandedLayout(
    state: SetupState,
    onAction: (SetupAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    val (titleRes, descriptionRes) = setupStepHeader(state.currentStep)

    if (state.showInviteMember) {
        InviteMemberDialog(onDismiss = { onAction(SetupAction.InviteMemberDismissed) })
    }

    Row(modifier = modifier) {
        SetupBrandPanel(
            currentStep = state.currentStep,
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
        )

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .padding(horizontal = 72.dp, vertical = 40.dp),
            contentAlignment = Alignment.TopCenter,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .widthIn(max = 460.dp),
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState()),
                ) {
                    SetupStepProgress(
                        currentStep = state.currentStep,
                        totalSteps = state.totalSteps,
                        modifier = Modifier.fillMaxWidth(),
                    )

                    Spacer(modifier = Modifier.height(28.dp))

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
                        useCenteredDialog = true,
                        modifier = Modifier.fillMaxWidth(),
                    )

                    Spacer(modifier = Modifier.height(24.dp))
                }

                Spacer(modifier = Modifier.height(12.dp))

                SetupSaveStatus(
                    state = state,
                    modifier = Modifier.fillMaxWidth(),
                )

                if (setupStepHasOwnFooter(state.currentStep)) {
                    SetupStepFooter(
                        state = state,
                        onAction = onAction,
                        modifier = Modifier.fillMaxWidth(),
                    )
                } else {
                    PrimaryButton(
                        text = stringResource(Res.string.continue_action),
                        onClick = { onAction(SetupAction.Continue) },
                        enabled = state.canContinue && !state.isSaving,
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
            }
        }
    }
}
