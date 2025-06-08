package com.shah.cashwise.ui.components.account

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shah.cashwise.R
import com.shah.cashwise.ui.components.common.PrimaryButton
import com.shah.cashwise.ui.components.common.PrimaryDropdown
import com.shah.cashwise.ui.components.common.PrimaryTextField
import com.shah.cashwise.ui.theme.CashWiseTheme
import com.shah.cashwise.utils.extensions.isValidDoubleNumber

/**
 * Created by Monil on 07/06/25.
 */

@Composable
fun AccountDetailsView(
    onContinueClick: (Double, String, String) -> Unit
) {
    val balance = remember { mutableDoubleStateOf(00.0) }
    var balanceString by remember { mutableStateOf(balance.doubleValue.toString()) }
    var name = ""
    var accountType = ""

    Column {
        Text(
            "Balance",
            modifier = Modifier.padding(horizontal = 16.dp),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.65f)
        )

        Row(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "₹",
                style = MaterialTheme.typography.displayLarge,
                color = MaterialTheme.colorScheme.onPrimary
            )

            TextField(
                value = balanceString,
                onValueChange = { value ->
                    balanceString = value
                    if (value.isValidDoubleNumber()) {
                        balance.doubleValue = value.toDouble()
                    }
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                singleLine = true,
                textStyle = MaterialTheme.typography.displayLarge,
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.Transparent,
                    unfocusedTextColor = MaterialTheme.colorScheme.onPrimary,
                    focusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = MaterialTheme.colorScheme.onPrimary,
                    focusedTextColor = MaterialTheme.colorScheme.onPrimary,

                )
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(264.dp)
                .background(
                    shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp),
                    color = MaterialTheme.colorScheme.background
                )
                .padding(horizontal = 16.dp, vertical = 24.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            PrimaryTextField(
                modifier = Modifier.fillMaxWidth(),
                label = "Name",
                singleLine = true
            ) {
                name = it
            }

            PrimaryDropdown(
                modifier = Modifier.fillMaxWidth(),
                label = "Account Type",
                listOf("", "", "")
            ) {
                accountType = it
            }

            Spacer(Modifier.height(16.dp))

            PrimaryButton(
                Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                stringResource(R.string.continue_),
                onClick = {
                    onContinueClick(balance.doubleValue, name, accountType)
                }
            )
        }
    }
}

@Preview
@Composable
fun PreviewAccountDetailsView() {
    CashWiseTheme {
        AccountDetailsView {_, _, _ -> }
    }
}
