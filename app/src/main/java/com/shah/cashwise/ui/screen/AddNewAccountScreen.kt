package com.shah.cashwise.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.shah.cashwise.ui.components.account.AccountDetailsView
import com.shah.cashwise.ui.components.nav.ScreenTopBar
import com.shah.cashwise.ui.theme.CashWiseTheme

/**
 * Created by Monil on 08/06/25.
 */

@Composable
fun AddNewAccountScreen(
    onBackClick: () -> Unit,
    onContinueClick: () -> Unit
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(it),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            ScreenTopBar(
                label = "Add New Account",
                endIcon = null,
                endIconDescription = null,
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                onBackButtonClick = onBackClick,
                onEndIconClick = {}
            )

            AccountDetailsView({ amount, name, accountType ->
                // Save data to room
                onContinueClick()
            })
        }
    }
}

@Preview
@Composable
fun PreviewAddNewAccountScreen() {
    CashWiseTheme {
        AddNewAccountScreen({}){}
    }
}
