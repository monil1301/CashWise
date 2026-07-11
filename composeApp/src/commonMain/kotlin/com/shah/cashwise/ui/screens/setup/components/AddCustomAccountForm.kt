package com.shah.cashwise.ui.screens.setup.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import cashwise.composeapp.generated.resources.Res
import cashwise.composeapp.generated.resources.setup_add_custom_account
import cashwise.composeapp.generated.resources.setup_custom_account_balance_label
import cashwise.composeapp.generated.resources.setup_custom_account_balance_placeholder
import cashwise.composeapp.generated.resources.setup_custom_account_close
import cashwise.composeapp.generated.resources.setup_custom_account_icon_label
import cashwise.composeapp.generated.resources.setup_custom_account_name_label
import cashwise.composeapp.generated.resources.setup_custom_account_name_placeholder
import com.shah.cashwise.domain.model.CustomAccountIcon
import com.shah.cashwise.ui.components.LabeledField
import com.shah.cashwise.ui.screens.setup.model.CustomAccountDraft
import com.shah.cashwise.ui.screens.setup.model.icon
import org.jetbrains.compose.resources.stringResource

/**
 * Shared body of the add-custom-account form — owns the field state and renders
 * the title, name, icon picker, and balance inputs. [actions] supplies the
 * presentation-specific footer (see [AddCustomAccountSheet] and
 * [AddCustomAccountDialog]) and receives the current [CustomAccountDraft] and
 * whether it is valid to submit.
 */
@Composable
internal fun AddCustomAccountForm(
    onClose: () -> Unit,
    modifier: Modifier = Modifier,
    actions: @Composable (draft: CustomAccountDraft, isValid: Boolean) -> Unit,
) {
    var name by rememberSaveable { mutableStateOf("") }
    var selectedIcon by rememberSaveable { mutableStateOf(CustomAccountIcon.Cash) }
    var startingBalance by rememberSaveable { mutableStateOf("") }

    val draft = CustomAccountDraft(
        name = name.trim(),
        icon = selectedIcon,
        startingBalance = startingBalance.trim(),
    )

    Column(modifier = modifier.fillMaxWidth()) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = stringResource(Res.string.setup_add_custom_account),
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.weight(1f),
            )

            FilledTonalIconButton(onClick = onClose) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = stringResource(Res.string.setup_custom_account_close),
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        LabeledField(label = stringResource(Res.string.setup_custom_account_name_label)) {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                placeholder = {
                    Text(text = stringResource(Res.string.setup_custom_account_name_placeholder))
                },
                singleLine = true,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.fillMaxWidth(),
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        LabeledField(label = stringResource(Res.string.setup_custom_account_icon_label)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                CustomAccountIcon.entries.forEach { option ->
                    IconChoice(
                        icon = option.icon(),
                        selected = option == selectedIcon,
                        onClick = { selectedIcon = option },
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        LabeledField(label = stringResource(Res.string.setup_custom_account_balance_label)) {
            OutlinedTextField(
                value = startingBalance,
                onValueChange = { startingBalance = it },
                leadingIcon = {
                    Text(
                        text = "₹",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                },
                placeholder = {
                    Text(text = stringResource(Res.string.setup_custom_account_balance_placeholder))
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.fillMaxWidth(),
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        actions(draft, name.isNotBlank())
    }
}
