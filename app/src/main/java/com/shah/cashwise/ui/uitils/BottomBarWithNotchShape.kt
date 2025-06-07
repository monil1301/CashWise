package com.shah.cashwise.ui.uitils

import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.Density

/**
 * Created by Monil on 26/05/25.
 */

class BottomBarWithNotchShape : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val width = size.width
        val height = size.height
        val radius = 200f // Notch width
        val depth = 110f // Notch depth

        val path = Path().apply {
            reset()
            lineTo(width / 2 - radius, 0f)
            cubicTo(
                width / 2 - radius / 2, 0f,
                width / 2 - radius / 2, depth,
                width / 2f, depth
            )
            cubicTo(
                width / 2 + radius / 2, depth,
                width / 2 + radius / 2, 0f,
                width / 2 + radius, 0f
            )
            lineTo(width, 0f)
            lineTo(width, height)
            lineTo(0f, height)
            close()
        }

        return Outline.Generic(path)
    }
}
