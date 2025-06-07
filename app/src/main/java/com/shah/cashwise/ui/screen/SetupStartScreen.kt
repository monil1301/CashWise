package com.shah.cashwise.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shah.cashwise.R
import com.shah.cashwise.ui.components.common.PrimaryButton
import com.shah.cashwise.ui.theme.CashWiseTheme

/**
 * Created by Monil on 07/06/25.
 */

@Composable
fun SetupStartScreen(onContinue: () -> Unit) {
    Scaffold {
        Column (
            modifier = Modifier.fillMaxSize().padding(it),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column (
                modifier = Modifier.padding(20.dp).padding(top = 50.dp)
            ) {
                Text(
                    text = stringResource(R.string.setup_start_header),
                    style = MaterialTheme.typography.displayLarge
                )

                Spacer(Modifier.height(36.dp))

                Text(
                    text = stringResource(R.string.setup_start_description),
                    style = MaterialTheme.typography.bodySmall
                )
            }

            PrimaryButton(
                modifier = Modifier.padding(16.dp).fillMaxWidth().height(56.dp),
                text = stringResource(R.string.setup_start_button),
                onClick = onContinue
            )
        }
    }
}

@Preview
@Composable
fun PreviewSetupStartScreen() {
    CashWiseTheme {
        SetupStartScreen {  }
    }
}
