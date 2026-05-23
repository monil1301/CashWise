package com.shah.cashwise.ui.screens.setup.steps

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.shah.cashwise.ui.screens.setup.SetupAction
import com.shah.cashwise.ui.screens.setup.components.AccountCard
import com.shah.cashwise.ui.screens.setup.components.AddCustomAccountDialog
import com.shah.cashwise.ui.screens.setup.components.AddCustomAccountSheet
import com.shah.cashwise.ui.screens.setup.model.CustomAccountDraft
import com.shah.cashwise.ui.screens.setup.model.SetupAccount

/**
 * Step 2 of setup — "Add your accounts": a list of toggleable [AccountCard]s
 * and an action to append a custom account.
 *
 * The add-custom-account form is presented as a centered [AddCustomAccountDialog]
 * when [useCenteredDialog] is set (the Expanded layout) and as an
 * [AddCustomAccountSheet] otherwise (phone-width layouts).
 */
@Composable
internal fun AddAccountsStep(
    accounts: List<SetupAccount>,
    onAction: (SetupAction) -> Unit,
    useCenteredDialog: Boolean,
    modifier: Modifier = Modifier,
) {
    var showAddCustomAccount by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        accounts.forEach { account ->
            AccountCard(
                account = account,
                onToggle = { enabled ->
                    onAction(SetupAction.AccountToggled(account.id, enabled))
                },
                onBalanceChange = { balance ->
                    onAction(SetupAction.AccountBalanceChanged(account.id, balance))
                },
                modifier = Modifier.fillMaxWidth(),
            )
        }

        AddCustomAccountButton(
            onClick = { showAddCustomAccount = true },
        )
    }

    if (showAddCustomAccount) {
        val onConfirm: (CustomAccountDraft) -> Unit = { draft ->
            onAction(SetupAction.CustomAccountAdded(draft))
            showAddCustomAccount = false
        }
        val onDismiss = { showAddCustomAccount = false }

        if (useCenteredDialog) {
            AddCustomAccountDialog(onConfirm = onConfirm, onDismiss = onDismiss)
        } else {
            AddCustomAccountSheet(onConfirm = onConfirm, onDismiss = onDismiss)
        }
    }
}
