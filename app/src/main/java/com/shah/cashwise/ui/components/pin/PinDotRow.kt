package com.shah.cashwise.ui.components.pin

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shah.cashwise.ui.theme.CashWiseTheme

/**
 * Created by Monil on 01/06/25.
 */

@Composable
fun PinDotRow(pinLength: Int, filledLength: Int, resetKey: Int) {
    val offsetX = remember { Animatable(0f) }

    if (resetKey > 0) {
        // Shake animation on reset
        LaunchedEffect(resetKey) {
            // Shake with a sequence of left-right translations
            val shakeMagnitude = 16f
            val durations = listOf(50L, 50L, 50L, 50L, 50L)
            val offsets =
                listOf(-shakeMagnitude, shakeMagnitude, -shakeMagnitude / 2, shakeMagnitude / 2, 0f)

            offsets.zip(durations).forEach { (value, duration) ->
                offsetX.animateTo(value, animationSpec = tween(durationMillis = duration.toInt()))
            }
        }
    }

    Row(
        modifier = Modifier.graphicsLayer {
            translationX = offsetX.value
        },
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
        PinDotRow(4, 1, 1)
    }
}
