package com.shah.cashwise.ui.components.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shah.cashwise.ui.components.common.PrimaryButton

/**
 * Created by Monil on 27/04/25.
 */

@Composable
fun SwitchAuthTypeHeader(isRegistering: Boolean, onSwitch: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp, horizontal = 16.dp),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            if (!isRegistering) "Don't have an account?" else "Already have an account?",
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )

        Spacer(modifier = Modifier.width(16.dp))

        PrimaryButton(
            Modifier
                .size(width = 100.dp, height = 40.dp),
            if (!isRegistering) "Get Started" else "Sign In",
            textStyle = MaterialTheme.typography.bodyMedium,
            contentPadding = PaddingValues(0.dp),
            onClick = onSwitch)
    }
}

@Preview
@Composable
fun PreviewSwitchAuthTypeHeader() {

    SwitchAuthTypeHeader(false) {}
}