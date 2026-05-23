package com.shah.cashwise.ui.screens.setup.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cashwise.composeapp.generated.resources.Res
import cashwise.composeapp.generated.resources.setup_custom_account_submit
import com.shah.cashwise.ui.components.PrimaryButton
import com.shah.cashwise.ui.screens.setup.model.CustomAccountDraft
import org.jetbrains.compose.resources.stringResource

/**
 * Compact presentation of the add-custom-account form — a modal bottom sheet.
 * Confirming reports a [CustomAccountDraft] through [onConfirm]; the caller
 * dismisses the sheet.
 *
 * Opened from [AddAccountsStep] on phone-width layouts; wider layouts use
 * [AddCustomAccountDialog] instead.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AddCustomAccountSheet(
    onConfirm: (CustomAccountDraft) -> Unit,
    onDismiss: () -> Unit,
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
    ) {
        AddCustomAccountForm(
            onClose = onDismiss,
            modifier = Modifier.padding(start = 24.dp, end = 24.dp, bottom = 24.dp),
        ) { draft, isValid ->
            PrimaryButton(
                text = stringResource(Res.string.setup_custom_account_submit),
                onClick = { onConfirm(draft) },
                enabled = isValid,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}
