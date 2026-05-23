package com.shah.cashwise.ui.screens.setup.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import cashwise.composeapp.generated.resources.Res
import cashwise.composeapp.generated.resources.cancel
import cashwise.composeapp.generated.resources.setup_custom_account_submit
import com.shah.cashwise.ui.components.PrimaryButton
import com.shah.cashwise.ui.screens.setup.model.CustomAccountDraft
import org.jetbrains.compose.resources.stringResource

/**
 * Wide-layout presentation of the add-custom-account form — a centered dialog
 * with Cancel / Add account actions. Confirming reports a [CustomAccountDraft]
 * through [onConfirm]; the caller dismisses the dialog.
 *
 * Opened from [AddAccountsStep] on the Expanded layout; phone-width layouts use
 * [AddCustomAccountSheet] instead.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AddCustomAccountDialog(
    onConfirm: (CustomAccountDraft) -> Unit,
    onDismiss: () -> Unit,
) {
    BasicAlertDialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false),
    ) {
        Surface(
            shape = MaterialTheme.shapes.extraLarge,
            color = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface,
            tonalElevation = 0.dp,
            shadowElevation = 8.dp,
            modifier = Modifier.widthIn(max = 480.dp).padding(24.dp),
        ) {
            AddCustomAccountForm(
                onClose = onDismiss,
                modifier = Modifier.padding(24.dp),
            ) { draft, isValid ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.End),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    TextButton(onClick = onDismiss) {
                        Text(
                            text = stringResource(Res.string.cancel),
                            style = MaterialTheme.typography.titleMedium,
                        )
                    }
                    PrimaryButton(
                        text = stringResource(Res.string.setup_custom_account_submit),
                        onClick = { onConfirm(draft) },
                        enabled = isValid,
                    )
                }
            }
        }
    }
}
