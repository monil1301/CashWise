package com.shah.cashwise.ui.components.pin

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shah.cashwise.ui.theme.CashWiseTheme

/**
 * Created by Monil on 01/06/25.
 */

@Composable
fun PinDotRow(pinLength: Int, filledLength: Int) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        repeat(pinLength) {
            PinDot(it < filledLength)
        }
    }
}

@Preview
@Composable
fun PreviewPinDotRow() {
    CashWiseTheme {
        PinDotRow(4, 1)
    }
}
