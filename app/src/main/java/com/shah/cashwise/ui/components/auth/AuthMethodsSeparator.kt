package com.shah.cashwise.ui.components.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/**
 * Created by Monil on 21/04/25.
 */

@Composable
fun AuthMethodsSeparator(isRegistering: Boolean = false) {
    Row (
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        HorizontalDivider(Modifier.weight(1f))

        Text(if (isRegistering) "Or Sign Up with" else "Or Login with")

        HorizontalDivider(Modifier.weight(1f))
    }
}

@Preview
@Composable
fun PreviewAuthMethodsSeparator() {
    AuthMethodsSeparator()
}