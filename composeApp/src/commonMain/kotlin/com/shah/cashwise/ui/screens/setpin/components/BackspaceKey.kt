package com.shah.cashwise.ui.screens.setpin.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Backspace
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cashwise.composeapp.generated.resources.Res
import cashwise.composeapp.generated.resources.set_pin_backspace
import org.jetbrains.compose.resources.stringResource

/** The backspace key on the [NumericKeypad]; deletes the last entered digit. */
@Composable
internal fun BackspaceKey(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    KeyButton(onClick = onClick, modifier = modifier) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.Backspace,
            contentDescription = stringResource(Res.string.set_pin_backspace),
            tint = MaterialTheme.colorScheme.primary,
        )
    }
}
