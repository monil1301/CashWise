package com.shah.cashwise.ui.components.auth

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shah.cashwise.ui.theme.CashWiseTheme

/**
 * Created by Monil on 21/04/25.
 */

@Composable
fun SignUpHeader() {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Get Started",
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(Modifier.height(12.dp))

        Text(
            "Fill up your details to get started",
            fontWeight = FontWeight.Light
        )
    }
}

@Preview
@Composable
fun PreviewSignUpHeader() {
    CashWiseTheme {
        SignUpHeader()
    }
}