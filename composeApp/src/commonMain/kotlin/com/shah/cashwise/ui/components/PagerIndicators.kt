package com.shah.cashwise.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun PagerIndicators(
    totalPages: Int,
    selectedPage: Int,
    modifier: Modifier = Modifier,
) {
    if (totalPages <= 0) return

    val safeSelectedPage = selectedPage.coerceIn(0, totalPages - 1)

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
    ) {
        repeat(totalPages) { page ->
            val isSelected = page == safeSelectedPage
            val indicatorColor = if (isSelected) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.outline
            }

            Box(
                modifier = Modifier
                    .clip(MaterialTheme.shapes.small)
                    .width(if (isSelected) 28.dp else 8.dp)
                    .height(8.dp)
                    .background(
                        color = indicatorColor,
                        shape = MaterialTheme.shapes.small,
                    )
            )
        }
    }
}
