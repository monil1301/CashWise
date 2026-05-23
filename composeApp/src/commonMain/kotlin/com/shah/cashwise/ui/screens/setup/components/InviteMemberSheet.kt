package com.shah.cashwise.ui.screens.setup.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Compact presentation of the "Invite a member" surface — a modal bottom
 * sheet. Opens fully expanded (matching the AddCustomAccountSheet behaviour);
 * the caller dismisses by handling [onDismiss].
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun InviteMemberSheet(
    onDismiss: () -> Unit,
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
    ) {
        InviteMemberSheetContent(
            onDismiss = onDismiss,
            modifier = Modifier.padding(start = 24.dp, end = 24.dp, bottom = 24.dp),
        )
    }
}
