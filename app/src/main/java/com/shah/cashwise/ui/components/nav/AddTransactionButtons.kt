package com.shah.cashwise.ui.components.nav

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shah.cashwise.R
import com.shah.cashwise.ui.components.common.CircleButtonWithIcon
import com.shah.cashwise.ui.theme.CashWiseTheme

/**
 * Created by Monil on 06/06/25.
 */

@Composable
fun AddTransactionButtons(modifier: Modifier = Modifier) {

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircleButtonWithIcon(
            painter = painterResource(R.drawable.transfer),
            contentDescription = "transfer",
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondary,
                contentColor = MaterialTheme.colorScheme.onSecondary
            )
        ) {}

        Spacer(Modifier.height(16.dp))

        Row(
            modifier = Modifier.width(192.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            CircleButtonWithIcon(
                painter = painterResource(R.drawable.income),
                contentDescription = "income",
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {}

            CircleButtonWithIcon(
                painter = painterResource(R.drawable.expense),
                contentDescription = "expense",
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error,
                    contentColor = MaterialTheme.colorScheme.onError
                )
            ) {}
        }
    }
}

@Preview
@Composable
fun PreviewAddTransactionButtons() {
    CashWiseTheme {
        AddTransactionButtons()
    }
}
