package com.shah.cashwise.ui.screens.setup.components

import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties

/**
 * Wide-layout presentation of the "Invite a member" surface — a centered
 * dialog with the QR on the left and the role / code / link form on the
 * right. Mirrors the AddCustomAccountDialog tonal-elevation treatment (0.dp
 * tonal, 8.dp shadow, explicit `contentColor = onSurface`) to avoid a green
 * tint regression.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun InviteMemberDialog(
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
            // requiredWidth (not width): BasicAlertDialog still imposes a max
            // width constraint on iOS even with usePlatformDefaultWidth = false,
            // so plain width()/widthIn() get clamped and the content collapses.
            // requiredWidth overrides the incoming constraint. The Expanded
            // breakpoint only fires at >= 840.dp, so 780.dp always fits.
            modifier = Modifier.requiredWidth(780.dp),
        ) {
            InviteMemberDialogContent(onDismiss = onDismiss)
        }
    }
}
