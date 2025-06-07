package com.shah.cashwise.ui.components.nav

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shah.cashwise.ui.theme.CashWiseTheme
import com.shah.cashwise.ui.theme.GrayAlto
import com.shah.cashwise.ui.uitils.InitialsAvatar

/**
 * Created by Monil on 25/05/25.
 */

@Composable
fun NavScreenHeader(
    screenName: String,
    userName: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .height(90.dp)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Button(
            {},
            modifier = Modifier.size(50.dp),
            shape = RoundedCornerShape(20),
            colors = ButtonDefaults.buttonColors(containerColor = Color.White),
            contentPadding = PaddingValues(0.dp)
        ) {
            InitialsAvatar(modifier = Modifier.fillMaxSize(), name = userName)
        }

        Text(
            screenName,
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onBackground
        )

        Button(
            onClick = {},
            modifier = Modifier.size(50.dp),
            shape = RoundedCornerShape(20),
            colors = ButtonDefaults.buttonColors(containerColor = GrayAlto),
            contentPadding = PaddingValues(0.dp)
        ) {
            Image(
                Icons.Default.Notifications,
                "Notifications"
            )
        }

    }
}

@Preview
@Composable
fun PreviewNavScreenHeader() {
    CashWiseTheme {
        NavScreenHeader("Home", "Monil Shah")
    }
}
