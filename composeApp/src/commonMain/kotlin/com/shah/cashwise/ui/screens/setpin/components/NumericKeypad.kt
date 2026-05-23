package com.shah.cashwise.ui.screens.setpin.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Phone-style 3 × 4 numeric keypad — digits 1–9, then a blank, 0, and a
 * backspace key. Each digit press fires [onDigit]; the backspace key fires
 * [onBackspace].
 */
@Composable
internal fun NumericKeypad(
    onDigit: (Char) -> Unit,
    onBackspace: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        KeypadRow {
            DigitKey(digit = '1', onClick = onDigit, modifier = Modifier.weight(1f))
            DigitKey(digit = '2', onClick = onDigit, modifier = Modifier.weight(1f))
            DigitKey(digit = '3', onClick = onDigit, modifier = Modifier.weight(1f))
        }
        KeypadRow {
            DigitKey(digit = '4', onClick = onDigit, modifier = Modifier.weight(1f))
            DigitKey(digit = '5', onClick = onDigit, modifier = Modifier.weight(1f))
            DigitKey(digit = '6', onClick = onDigit, modifier = Modifier.weight(1f))
        }
        KeypadRow {
            DigitKey(digit = '7', onClick = onDigit, modifier = Modifier.weight(1f))
            DigitKey(digit = '8', onClick = onDigit, modifier = Modifier.weight(1f))
            DigitKey(digit = '9', onClick = onDigit, modifier = Modifier.weight(1f))
        }
        KeypadRow {
            Spacer(modifier = Modifier.weight(1f))
            DigitKey(digit = '0', onClick = onDigit, modifier = Modifier.weight(1f))
            BackspaceKey(onClick = onBackspace, modifier = Modifier.weight(1f))
        }
    }
}
