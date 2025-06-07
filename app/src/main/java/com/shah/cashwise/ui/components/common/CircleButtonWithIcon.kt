package com.shah.cashwise.ui.components.common

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shah.cashwise.R
import com.shah.cashwise.ui.theme.CashWiseTheme

/**
 * Created by Monil on 06/06/25.
 */

@Composable
fun CircleButtonWithIcon(
    painter: Painter,
    contentDescription: String,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    onClick: () -> Unit
) {
    Button(
        modifier = Modifier.size(56.dp),
        onClick = onClick,
        shape = CircleShape,
        colors = colors,
        contentPadding = PaddingValues(0.dp)
    ) {
        Icon(
            painter = painter, contentDescription
        )
    }
}

@Preview
@Composable
fun PreviewCircleButtonWithIcon() {
    CashWiseTheme {
        CircleButtonWithIcon(painterResource(R.drawable.income), "income") {}
    }
}
