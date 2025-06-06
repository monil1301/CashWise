package com.shah.cashwise.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.shah.cashwise.R
import com.shah.cashwise.ui.components.pin.PinInputView
import com.shah.cashwise.ui.theme.CashWiseTheme
import com.shah.cashwise.ui.viewmodel.PinUnlockViewModel

/**
 * Created by Monil on 03/06/25.
 */

@Composable
fun PinUnlockScreen(viewModel: PinUnlockViewModel = hiltViewModel(), onPinMatch: () -> Unit) {
    var resetKey by remember { mutableIntStateOf(0) } // Trigger for shake

    Box {
        PinInputView(stringResource(R.string.unlock_with_pin), resetKey) { pin ->
            viewModel.doesPinMatch(pin) { doesMatch ->
                if (doesMatch)
                    onPinMatch()
                else
                    resetKey++
            }
        }
    }
}

@Preview
@Composable
fun PreviewPinUnlockScreen() {
    CashWiseTheme {
        PinUnlockScreen {  }
    }
}
