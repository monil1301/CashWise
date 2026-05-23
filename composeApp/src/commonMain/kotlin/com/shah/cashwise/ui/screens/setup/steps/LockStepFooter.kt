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
import cashwise.composeapp.generated.resources.setup_lock_not_now
import cashwise.composeapp.generated.resources.setup_lock_set_pin
import com.shah.cashwise.ui.components.PrimaryButton
import com.shah.cashwise.ui.screens.setup.SetupAction
import org.jetbrains.compose.resources.stringResource

/**
 * Pinned footer for the Lock step — "Set PIN" (opens [SetPinScreen]) and
 * "Not now" (disables app lock and advances). Replaces the layout's default
 * Continue button via [SetupStepFooter].
 */
@Composable
internal fun LockStepFooter(
    onAction: (SetupAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        PrimaryButton(
            text = stringResource(Res.string.setup_lock_set_pin),
            onClick = { onAction(SetupAction.OpenSetPin) },
            modifier = Modifier.fillMaxWidth(),
        )

        TextButton(
            onClick = { onAction(SetupAction.AppLockSkipped) },
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(
                text = stringResource(Res.string.setup_lock_not_now),
                style = MaterialTheme.typography.titleMedium,
            )
        }
    }
}
