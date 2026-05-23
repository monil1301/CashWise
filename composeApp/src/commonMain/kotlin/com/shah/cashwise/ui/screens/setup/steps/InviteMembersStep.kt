package com.shah.cashwise.ui.screens.setup.steps

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Link
import androidx.compose.material.icons.outlined.QrCode2
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cashwise.composeapp.generated.resources.Res
import cashwise.composeapp.generated.resources.setup_invite_member_you
import cashwise.composeapp.generated.resources.setup_invite_members_label
import cashwise.composeapp.generated.resources.setup_invite_role_owner
import cashwise.composeapp.generated.resources.setup_invite_show_qr
import cashwise.composeapp.generated.resources.setup_invite_via_link
import com.shah.cashwise.ui.components.LabeledField
import com.shah.cashwise.ui.screens.setup.SetupAction
import com.shah.cashwise.ui.screens.setup.components.InviteActionButton
import com.shah.cashwise.ui.screens.setup.components.MemberRow
import org.jetbrains.compose.resources.stringResource

/**
 * Step 5 of setup (Shared wallets only) — "Invite members": the labelled
 * members list seeded with the current user as Owner, plus the "Invite via
 * link" / "Show QR code" outlined actions. Buttons live in
 * [InviteMembersFooter] so they stay pinned at the bottom of the layout.
 */
@Composable
internal fun InviteMembersStep(
    onAction: (SetupAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        LabeledField(label = stringResource(Res.string.setup_invite_members_label).uppercase()) {
            MemberRow(
                name = stringResource(Res.string.setup_invite_member_you),
                role = stringResource(Res.string.setup_invite_role_owner),
                modifier = Modifier.fillMaxWidth(),
            )
        }

        InviteActionButton(
            icon = Icons.Outlined.Link,
            label = stringResource(Res.string.setup_invite_via_link),
            onClick = { onAction(SetupAction.InviteViaLinkClicked) },
            modifier = Modifier.fillMaxWidth(),
        )

        InviteActionButton(
            icon = Icons.Outlined.QrCode2,
            label = stringResource(Res.string.setup_invite_show_qr),
            onClick = { onAction(SetupAction.ShowQrCodeClicked) },
            modifier = Modifier.fillMaxWidth(),
        )
    }
}
