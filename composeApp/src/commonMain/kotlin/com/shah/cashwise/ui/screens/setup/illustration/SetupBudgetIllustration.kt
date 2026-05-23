package com.shah.cashwise.ui.screens.setup.illustration

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

/**
 * Decorative artwork for the "Set your first budget" step shown on the brand
 * panel of [SetupExpandedLayout] — a two-tone donut chart on a soft tile, with
 * gold coins scattered around it. Mirrors the shape of [SetupWalletIllustration].
 */
@Composable
internal fun SetupBudgetIllustration(
    modifier: Modifier = Modifier,
) {
    val donutPrimary = MaterialTheme.colorScheme.primary
    val donutSecondary = MaterialTheme.colorScheme.primary.copy(alpha = 0.35f)
    val coinColor = Color(0xFFF1B82D)
    val coinShine = Color(0xFFFFD66B)

    Box(
        modifier = modifier
            .size(280.dp)
            .clip(RoundedCornerShape(32.dp))
            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.18f)),
        contentAlignment = Alignment.Center,
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            // Donut chart, centred.
            val donutDiameter = size.minDimension * 0.6f
            val stroke = donutDiameter * 0.28f
            val arcSize = Size(donutDiameter - stroke, donutDiameter - stroke)
            val arcTopLeft = Offset(
                x = center.x - arcSize.width / 2,
                y = center.y - arcSize.height / 2,
            )

            drawArc(
                color = donutPrimary,
                startAngle = -90f,
                sweepAngle = 240f,
                useCenter = false,
                topLeft = arcTopLeft,
                size = arcSize,
                style = Stroke(width = stroke),
            )
            drawArc(
                color = donutSecondary,
                startAngle = 150f,
                sweepAngle = 120f,
                useCenter = false,
                topLeft = arcTopLeft,
                size = arcSize,
                style = Stroke(width = stroke),
            )

            // Scattered coins.
            val coinRadius = size.minDimension * 0.065f
            val coinPositions = listOf(
                Offset(0.22f, 0.26f),
                Offset(0.14f, 0.62f),
                Offset(0.78f, 0.48f),
                Offset(0.58f, 0.86f),
            )
            coinPositions.forEach { pos ->
                val centerPx = Offset(pos.x * size.width, pos.y * size.height)
                drawCircle(color = coinColor, radius = coinRadius, center = centerPx)
                drawCircle(
                    color = coinShine,
                    radius = coinRadius * 0.55f,
                    center = centerPx.copy(
                        x = centerPx.x - coinRadius * 0.18f,
                        y = centerPx.y - coinRadius * 0.18f,
                    ),
                )
            }
        }
    }
}
