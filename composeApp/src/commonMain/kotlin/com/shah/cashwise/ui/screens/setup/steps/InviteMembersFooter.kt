package com.shah.cashwise.ui.screens.setup.steps

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cashwise.composeapp.generated.resources.Res
import cashwise.composeapp.generated.resources.setup_invite_done
import cashwise.composeapp.generated.resources.setup_invite_skip_caption
import com.shah.cashwise.ui.components.PrimaryButton
import com.shah.cashwise.ui.screens.setup.SetupAction
import org.jetbrains.compose.resources.stringResource

/**
 * Pinned footer for the [InviteMembersStep] — the "Done" button finishes setup (members
 * can be added later from wallet settings); the caption sits beneath as a reassurance.
 *
 * This is the last step of a Shared wallet, so Done commits the setup to the database and
 * is disabled while [saving] so a second tap cannot start a second write.
 */
@Composable
internal fun InviteMembersFooter(
    saving: Boolean,
    onAction: (SetupAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        PrimaryButton(
            text = stringResource(Res.string.setup_invite_done),
            onClick = { onAction(SetupAction.InviteMembersDone) },
            enabled = !saving,
            modifier = Modifier.fillMaxWidth(),
        )

        Text(
            text = stringResource(Res.string.setup_invite_skip_caption),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}
