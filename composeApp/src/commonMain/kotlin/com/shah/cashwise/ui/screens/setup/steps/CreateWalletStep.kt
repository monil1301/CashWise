package com.shah.cashwise.ui.screens.setup.steps

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cashwise.composeapp.generated.resources.Res
import cashwise.composeapp.generated.resources.setup_currency_label
import cashwise.composeapp.generated.resources.setup_wallet_name_label
import cashwise.composeapp.generated.resources.setup_wallet_name_placeholder
import cashwise.composeapp.generated.resources.setup_wallet_type_label
import com.shah.cashwise.domain.model.Currency
import com.shah.cashwise.domain.model.WalletType
import com.shah.cashwise.ui.components.CurrencySelectorField
import com.shah.cashwise.ui.components.LabeledField
import com.shah.cashwise.ui.screens.setup.SetupAction
import com.shah.cashwise.ui.screens.setup.components.WalletTypeSelector
import org.jetbrains.compose.resources.stringResource

/**
 * Step 1 of setup — "Create your first wallet": name, type, and currency.
 */
@Composable
internal fun CreateWalletStep(
    walletName: String,
    walletType: WalletType,
    selectedCurrency: Currency,
    onAction: (SetupAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(24.dp),
    ) {
        LabeledField(label = stringResource(Res.string.setup_wallet_name_label)) {
            OutlinedTextField(
                value = walletName,
                onValueChange = { onAction(SetupAction.WalletNameChanged(it)) },
                placeholder = {
                    Text(text = stringResource(Res.string.setup_wallet_name_placeholder))
                },
                singleLine = true,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.fillMaxWidth(),
            )
        }

        LabeledField(label = stringResource(Res.string.setup_wallet_type_label)) {
            WalletTypeSelector(
                selectedType = walletType,
                onTypeSelected = { onAction(SetupAction.WalletTypeChanged(it)) },
                modifier = Modifier.fillMaxWidth(),
            )
        }

        LabeledField(label = stringResource(Res.string.setup_currency_label)) {
            CurrencySelectorField(
                currency = selectedCurrency,
                onCurrencySelected = { onAction(SetupAction.CurrencySelected(it)) },
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}
