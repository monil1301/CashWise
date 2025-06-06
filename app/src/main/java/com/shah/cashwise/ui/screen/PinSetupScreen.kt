package com.shah.cashwise.ui.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.shah.cashwise.R
import com.shah.cashwise.ui.components.pin.PinInputView
import com.shah.cashwise.ui.theme.CashWiseTheme
import com.shah.cashwise.ui.viewmodel.PinSetupViewModel

/**
 * Created by Monil on 05/06/25.
 */

@Composable
fun PinSetupScreen(viewModel: PinSetupViewModel = hiltViewModel(), onSetupSuccess: () -> Unit) {
    var unlockPin by remember { mutableStateOf("") }
    var resetKey by remember { mutableIntStateOf(0) } // Trigger for shake

    Box {
        PinInputView(stringResource(R.string.confirm_pin), resetKey) { pin ->
            if (pin == unlockPin) {
                viewModel.saveUnlockPin(pin)
                onSetupSuccess()
            } else {
                resetKey++
                unlockPin = ""
            }
        }

        AnimatedVisibility(
            visible = unlockPin.isBlank(),
            enter = slideInHorizontally(
                initialOffsetX = { it }
            ),
            exit = slideOutHorizontally(
                targetOffsetX = { -it },
                animationSpec = tween(durationMillis = 500)
            ) + fadeOut(animationSpec = tween(500))
        ) {
            PinInputView(stringResource(R.string.setup_pin), 0) { pin ->
                unlockPin = pin
            }
        }
    }
}

@Preview
@Composable
fun PreviewPinSetupScreen() {
    CashWiseTheme {
        PinSetupScreen {}
    }
}
