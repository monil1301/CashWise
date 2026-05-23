package com.shah.cashwise.ui.screens.setup.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cashwise.composeapp.generated.resources.Res
import cashwise.composeapp.generated.resources.invite_code_label
import cashwise.composeapp.generated.resources.invite_expires
import cashwise.composeapp.generated.resources.invite_link_label
import cashwise.composeapp.generated.resources.invite_member_close
import cashwise.composeapp.generated.resources.invite_member_title
import cashwise.composeapp.generated.resources.invite_reset
import cashwise.composeapp.generated.resources.invite_role_label
import com.shah.cashwise.core.utils.rememberShareText
import com.shah.cashwise.domain.model.MemberRole
import com.shah.cashwise.ui.components.LabeledField
import com.shah.cashwise.ui.screens.setup.model.InviteCredentialsSaver
import com.shah.cashwise.ui.screens.setup.model.generateInviteCredentials
import com.shah.cashwise.ui.screens.setup.model.hintRes
import org.jetbrains.compose.resources.stringResource

/**
 * Stacked body of the "Invite a member" surface used by [InviteMemberSheet]
 * (compact phone width). Holds the local role + invite-credentials state and
 * renders the title, role selector, QR card, invite code, invite link, and the
 * footer with expires-caption / reset / share actions.
 */
@Composable
internal fun InviteMemberSheetContent(
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var selectedRole by rememberSaveable { mutableStateOf(MemberRole.Member) }
    var credentials by rememberSaveable(stateSaver = InviteCredentialsSaver) {
        mutableStateOf(generateInviteCredentials())
    }
    val shareText = rememberShareText()
    val shareTitle = stringResource(Res.string.invite_member_title)

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = stringResource(Res.string.invite_member_title),
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.weight(1f),
            )
            FilledTonalIconButton(onClick = onDismiss) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = stringResource(Res.string.invite_member_close),
                )
            }
        }

        LabeledField(label = stringResource(Res.string.invite_role_label).uppercase()) {
            MemberRoleSelector(
                selected = selectedRole,
                onSelect = { selectedRole = it },
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(selectedRole.hintRes()),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }

        InviteQrCard(
            link = credentials.link,
            modifier = Modifier
                .widthIn(max = 280.dp)
                .align(Alignment.CenterHorizontally),
        )

        LabeledField(label = stringResource(Res.string.invite_code_label).uppercase()) {
            InviteCodeBlock(code = credentials.code)
        }

        LabeledField(label = stringResource(Res.string.invite_link_label).uppercase()) {
            InviteLinkField(link = credentials.link)
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = stringResource(Res.string.invite_expires, credentials.expiresInDays),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.weight(1f),
            )
            TextButton(onClick = { credentials = generateInviteCredentials() }) {
                Text(
                    text = stringResource(Res.string.invite_reset),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary,
                )
            }
        }

        InviteShareButton(
            onClick = { shareText(credentials.link, shareTitle) },
            modifier = Modifier.fillMaxWidth(),
        )
    }
}
