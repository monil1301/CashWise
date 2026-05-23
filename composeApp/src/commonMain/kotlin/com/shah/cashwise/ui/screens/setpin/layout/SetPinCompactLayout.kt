package com.shah.cashwise.ui.screens.setpin.layout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cashwise.composeapp.generated.resources.Res
import cashwise.composeapp.generated.resources.set_pin_confirm_description
import cashwise.composeapp.generated.resources.set_pin_confirm_heading
import cashwise.composeapp.generated.resources.set_pin_confirm_title
import cashwise.composeapp.generated.resources.set_pin_create_description
import cashwise.composeapp.generated.resources.set_pin_create_heading
import cashwise.composeapp.generated.resources.set_pin_create_title
import cashwise.composeapp.generated.resources.set_pin_mismatch
import cashwise.composeapp.generated.resources.set_pin_start_over
import com.shah.cashwise.ui.screens.setpin.SetPinAction
import com.shah.cashwise.ui.screens.setpin.SetPinPhase
import com.shah.cashwise.ui.screens.setpin.SetPinState
import com.shah.cashwise.ui.screens.setpin.components.NumericKeypad
import com.shah.cashwise.ui.screens.setpin.components.PinDotsIndicator
import org.jetbrains.compose.resources.stringResource

private val HorizontalGutter = 24.dp

/**
 * Phone setup of the Set PIN screen — top bar, centred header and dots, then
 * the [NumericKeypad] pushed to the bottom with a "Start over" affordance
 * during the Confirm phase. Also reused, width-capped, by [SetPinMediumLayout].
 */
@Composable
internal fun SetPinCompactLayout(
    state: SetPinState,
    onAction: (SetPinAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    val topBarTitle = when (state.phase) {
        SetPinPhase.Create -> Res.string.set_pin_create_title
        SetPinPhase.Confirm -> Res.string.set_pin_confirm_title
    }
    val heading = when (state.phase) {
        SetPinPhase.Create -> Res.string.set_pin_create_heading
        SetPinPhase.Confirm -> Res.string.set_pin_confirm_heading
    }
    val description = when (state.phase) {
        SetPinPhase.Create -> Res.string.set_pin_create_description
        SetPinPhase.Confirm -> Res.string.set_pin_confirm_description
    }

    Column(modifier = modifier) {
        SetPinTopBar(
            title = topBarTitle,
            onBack = { onAction(SetPinAction.Back) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = HorizontalGutter, vertical = 8.dp),
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = HorizontalGutter),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            SetPinHeader(
                title = heading,
                description = description,
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(modifier = Modifier.height(20.dp))

            PinDotsIndicator(
                length = state.pinLength,
                filled = state.pin.length,
            )

            if (state.mismatch) {
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = stringResource(Res.string.set_pin_mismatch),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.error,
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = HorizontalGutter),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            NumericKeypad(
                onDigit = { onAction(SetPinAction.DigitPressed(it)) },
                onBackspace = { onAction(SetPinAction.BackspacePressed) },
            )

            if (state.phase == SetPinPhase.Confirm) {
                TextButton(
                    onClick = { onAction(SetPinAction.StartOver) },
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(
                        text = stringResource(Res.string.set_pin_start_over),
                        style = MaterialTheme.typography.titleMedium,
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))
    }
}
