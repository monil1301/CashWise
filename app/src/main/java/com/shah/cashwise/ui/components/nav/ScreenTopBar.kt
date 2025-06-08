package com.shah.cashwise.ui.components.nav

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shah.cashwise.R
import com.shah.cashwise.ui.theme.CashWiseTheme

/**
 * Created by Monil on 08/06/25.
 */

@Composable
fun ScreenTopBar(
    label: String,
    endIcon: Int?,
    endIconDescription: String?,
    containerColor: Color,
    contentColor: Color,
    onBackButtonClick: () -> Unit,
    onEndIconClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .background(containerColor)
            .fillMaxWidth()
            .height(60.dp)
            .padding(vertical = 16.dp, horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(
            onClick = onBackButtonClick,
        ) {
            Icon(
                painterResource(R.drawable.arrow_left),
                "Arrow Left",
                tint = contentColor
            )
        }

        Text(
            label,
            color = contentColor,
            style = MaterialTheme.typography.titleSmall
        )

        endIcon?.let {
            IconButton(
                onClick = onEndIconClick
            ) {
                Icon(
                    painterResource(it),
                    endIconDescription,
                    tint = contentColor
                )
            }
        } ?: Box (Modifier.size(36.dp)) { }

    }
}

@Preview
@Composable
fun PreviewScreenTopBar() {
    CashWiseTheme {
        ScreenTopBar(
            label = "Add New Account",
            endIcon = R.drawable.trash,
            endIconDescription = null,
            MaterialTheme.colorScheme.primary,
            MaterialTheme.colorScheme.onPrimary,
            {},
            {}
        )
    }
}
