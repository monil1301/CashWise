package com.shah.cashwise.ui.screens.setpin.layout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
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
import cashwise.composeapp.generated.resources.set_pin_create_description
import cashwise.composeapp.generated.resources.set_pin_create_heading
import cashwise.composeapp.generated.resources.set_pin_mismatch
import cashwise.composeapp.generated.resources.set_pin_start_over
import com.shah.cashwise.ui.screens.setpin.SetPinAction
import com.shah.cashwise.ui.screens.setpin.SetPinPhase
import com.shah.cashwise.ui.screens.setpin.SetPinState
import com.shah.cashwise.ui.screens.setpin.components.NumericKeypad
import com.shah.cashwise.ui.screens.setpin.components.PinDotsIndicator
import org.jetbrains.compose.resources.stringResource

private val FormMaxWidth = 460.dp

/**
 * Split Set PIN layout for tablet-landscape / desktop: the [SetPinBrandPanel]
 * on the left, the PIN form on the right. Mirrors [SetupExpandedLayout]'s
 * two-pane shape — like Setup's expanded layout, there is no back button (the
 * user completes or abandons via the keypad / system back). Only [SetPinHeader]
 * copy and the "Start over" affordance differ between Create and Confirm.
 */
@Composable
internal fun SetPinExpandedLayout(
    state: SetPinState,
    onAction: (SetPinAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    val heading = when (state.phase) {
        SetPinPhase.Create -> Res.string.set_pin_create_heading
        SetPinPhase.Confirm -> Res.string.set_pin_confirm_heading
    }
    val description = when (state.phase) {
        SetPinPhase.Create -> Res.string.set_pin_create_description
        SetPinPhase.Confirm -> Res.string.set_pin_confirm_description
    }

    Row(modifier = modifier.fillMaxSize()) {
        SetPinBrandPanel(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
        )

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .padding(horizontal = 72.dp, vertical = 40.dp),
            contentAlignment = Alignment.Center,
        ) {
            Column(
                modifier = Modifier.widthIn(max = FormMaxWidth),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(24.dp),
            ) {
                SetPinHeader(
                    title = heading,
                    description = description,
                    modifier = Modifier.fillMaxWidth(),
                )

                PinDotsIndicator(
                    length = state.pinLength,
                    filled = state.pin.length,
                )

                if (state.mismatch) {
                    Text(
                        text = stringResource(Res.string.set_pin_mismatch),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.error,
                    )
                }

                NumericKeypad(
                    onDigit = { onAction(SetPinAction.DigitPressed(it)) },
                    onBackspace = { onAction(SetPinAction.BackspacePressed) },
                    modifier = Modifier.fillMaxWidth(),
                )

                if (state.phase == SetPinPhase.Confirm) {
                    TextButton(
                        onClick = { onAction(SetPinAction.StartOver) },
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Text(
                            text = stringResource(Res.string.set_pin_start_over).uppercase(),
                            style = MaterialTheme.typography.titleMedium,
                        )
                    }
                }
            }
        }
    }
}
