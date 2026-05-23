package com.shah.cashwise.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

/**
 * A row of equal-width rounded segments showing progress through a multi-step
 * flow. Segments up to and including [currentStep] are filled; the rest are
 * shown in the outline colour. Stateless — siblings [PagerIndicators].
 */
@Composable
fun SegmentedProgressIndicator(
    totalSteps: Int,
    currentStep: Int,
    modifier: Modifier = Modifier,
) {
    if (totalSteps <= 0) return

    val safeCurrentStep = currentStep.coerceIn(0, totalSteps - 1)

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        repeat(totalSteps) { index ->
            val isFilled = index <= safeCurrentStep
            val color = if (isFilled) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.outline
            }

            Row(
                modifier = Modifier
                    .weight(1f)
                    .height(4.dp)
                    .clip(MaterialTheme.shapes.small)
                    .background(color),
            ) {}
        }
    }
}
