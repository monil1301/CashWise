package com.shah.cashwise.ui.screens.setup.steps

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cashwise.composeapp.generated.resources.Res
import cashwise.composeapp.generated.resources.setup_budget_set
import cashwise.composeapp.generated.resources.setup_budget_skip
import com.shah.cashwise.ui.components.PrimaryButton
import com.shah.cashwise.ui.screens.setup.SetupAction
import org.jetbrains.compose.resources.stringResource

/**
 * Pinned footer for the [SetBudgetStep] — "Set budget" (finishes setup with
 * the entered values, enabled only when [submitEnabled]) and "Skip for now"
 * (finishes setup without recording a budget; always enabled). Both currently
 * complete setup; persistence is a TODO.
 */
@Composable
internal fun BudgetStepFooter(
    submitEnabled: Boolean,
    onAction: (SetupAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        PrimaryButton(
            text = stringResource(Res.string.setup_budget_set),
            onClick = { onAction(SetupAction.BudgetSet) },
            enabled = submitEnabled,
            modifier = Modifier.fillMaxWidth(),
        )

        TextButton(
            onClick = { onAction(SetupAction.BudgetSkipped) },
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(
                text = stringResource(Res.string.setup_budget_skip),
                style = MaterialTheme.typography.titleMedium,
            )
        }
    }
}
