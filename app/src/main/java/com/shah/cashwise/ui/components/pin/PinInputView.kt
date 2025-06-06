package com.shah.cashwise.ui.components.pin

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shah.cashwise.ui.components.pin.NumericPad
import com.shah.cashwise.ui.components.pin.PinDotRow
import com.shah.cashwise.ui.theme.CashWiseTheme

/**
 * Created by Monil on 01/06/25.
 */

@Composable
fun PinInputView(title: String, resetKey: Int, onPinComplete: (String) -> Unit) {
    var pinLength by remember { mutableIntStateOf(0) }
    val pin = StringBuilder()

    if (resetKey > 0) {
        pin.clear()
        pinLength = 0
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 60.dp, vertical = 46.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(title, style = MaterialTheme.typography.titleSmall)

                Spacer(Modifier.height(92.dp))

                PinDotRow(4, pinLength, resetKey)
            }

            NumericPad({
                if (pin.isNotEmpty()) {
                    pin.deleteCharAt(pin.length - 1)
                    pinLength--
                }
            }) {
                pin.append(it)
                pinLength++

                if (pinLength == 4) onPinComplete(pin.toString())
            }
        }
    }
}

@Preview
@Composable
fun PreviewPinScreen() {
    CashWiseTheme {
        PinInputView("Let’s  setup your PIN", 0) {}
    }
}
