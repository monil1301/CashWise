package com.shah.cashwise.ui.screens.setup.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import cashwise.composeapp.generated.resources.Res
import cashwise.composeapp.generated.resources.setup_account_balance_placeholder
import com.shah.cashwise.ui.screens.setup.model.SetupAccount
import com.shah.cashwise.ui.screens.setup.model.icon
import com.shah.cashwise.ui.screens.setup.model.nameRes
import org.jetbrains.compose.resources.stringResource

/**
 * A single account row in setup step 2 — icon badge, name, on/off [Switch],
 * and (when enabled) an optional starting-balance field.
 */
@Composable
internal fun AccountCard(
    account: SetupAccount,
    onToggle: (Boolean) -> Unit,
    onBalanceChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    OutlinedCard(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                AccountIconBadge(account.customIcon?.icon() ?: account.kind.icon())

                Spacer(modifier = Modifier.width(12.dp))

                Text(
                    text = account.customName.ifBlank { stringResource(account.kind.nameRes()) },
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.weight(1f),
                )

                Switch(
                    checked = account.enabled,
                    onCheckedChange = onToggle,
                )
            }

            if (account.enabled) {
                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = account.startingBalance,
                    onValueChange = onBalanceChange,
                    leadingIcon = {
                        Text(
                            text = "₹",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    },
                    placeholder = {
                        Text(text = stringResource(Res.string.setup_account_balance_placeholder))
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    shape = MaterialTheme.shapes.medium,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }
    }
}
