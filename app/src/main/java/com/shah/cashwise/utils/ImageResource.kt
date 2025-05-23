package com.shah.cashwise.utils

import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Created by Monil on 20/04/25.
 */

sealed class ImageResource {
    data class IconResource(val icon: ImageVector, val contentDescription: String) : ImageResource()
    data class PainterResource(val painter: Painter, val contentDescription: String) : ImageResource()
    data object None: ImageResource()
}