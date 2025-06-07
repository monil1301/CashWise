package com.shah.cashwise.ui.components.nav

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp

/**
 * Created by Monil on 07/06/25.
 */

@Composable
fun BottomNavBarItem(
    painter: Painter,
    contentDescription: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val color =
        if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurface.copy(
            alpha = 0.6f
        )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconButton(
            onClick = onClick,
        ) {
            Icon(
                painter,
                contentDescription,
                tint = color
            )
        }

        Text(
            contentDescription,
            modifier = Modifier.offset(y = (-5).dp),
            color = color,
            style = MaterialTheme.typography.labelSmall
        )
    }
}
