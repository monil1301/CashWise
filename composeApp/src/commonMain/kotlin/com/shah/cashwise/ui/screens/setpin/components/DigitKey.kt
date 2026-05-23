package com.shah.cashwise.ui.screens.setpin.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/** A single digit key on the [NumericKeypad]; reports its [digit] on press. */
@Composable
internal fun DigitKey(
    digit: Char,
    onClick: (Char) -> Unit,
    modifier: Modifier = Modifier,
) {
    KeyButton(onClick = { onClick(digit) }, modifier = modifier) {
        Text(
            text = digit.toString(),
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onSurface,
        )
    }
}
