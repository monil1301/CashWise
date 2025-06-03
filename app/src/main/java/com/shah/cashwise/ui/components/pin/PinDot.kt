package com.shah.cashwise.ui.components.pin

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.shah.cashwise.ui.theme.CashWiseTheme

/**
 * Created by Monil on 01/06/25.
 */

@Composable
fun PinDot(
    isFilled: Boolean,
    selectedColor: Color = MaterialTheme.colorScheme.onPrimary,
    size: Dp = 32.dp
) {
    Box(
        modifier = Modifier
            .size(size)
            .clip(CircleShape)
            .background(if (isFilled) selectedColor else Color.Transparent)
            .border(width = 4.dp, shape = CircleShape, brush = SolidColor(selectedColor))
    )
}

@Preview
@Composable
fun PreviewPinDot() {
    CashWiseTheme {
        PinDot(false)
    }
}
