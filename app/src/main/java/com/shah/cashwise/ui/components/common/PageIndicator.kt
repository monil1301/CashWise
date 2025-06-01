package com.shah.cashwise.ui.components.common

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.shah.cashwise.ui.theme.CashWiseTheme

/**
 * Created by Monil on 01/06/25.
 */

@Composable
fun PageIndicator(
    isSelected: Boolean,
    selectedColor: Color = MaterialTheme.colorScheme.primary,
    size: Dp = 8.dp
) {
    val unselectedColor = selectedColor.copy(alpha = 0.3f)
    val animatedSize by animateDpAsState(
        targetValue = if (isSelected) size * 2 else size,
        animationSpec = tween(durationMillis = 200),
        label = "PageIndicatorGrowthAnimation"
    )

    Box(
        modifier = Modifier
            .size(animatedSize)
            .clip(CircleShape)
            .background(if (isSelected) selectedColor else unselectedColor)
    )
}

@Preview
@Composable
fun PreviewPageIndicator() {
    CashWiseTheme {
        PageIndicator(false)
    }
}
