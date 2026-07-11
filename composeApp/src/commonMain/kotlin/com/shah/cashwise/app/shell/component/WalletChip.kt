package com.shah.cashwise.app.shell.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.outlined.AccountBalanceWallet
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import cashwise.composeapp.generated.resources.Res
import cashwise.composeapp.generated.resources.shell_switch_wallet
import cashwise.composeapp.generated.resources.sync_status_synced
import com.shah.cashwise.domain.model.SyncStatus
import com.shah.cashwise.ui.components.SyncStatusDot
import org.jetbrains.compose.resources.stringResource

/**
 * The top bar's identity: which wallet you are looking at, and — when all is well — a small
 * green dot saying so.
 *
 * The dot appears *only* for [SyncStatus.Synced]. In every other state the top bar shows a
 * labelled pill instead (see [com.shah.cashwise.ui.components.SyncStatusPill]), so the two
 * are mutually exclusive by construction and the user is never told two things at once.
 *
 * @param onClick opens the wallet picker. Ignored when [canSwitch] is false — with a single
 *  wallet there is nothing to switch to, so the chip is inert and shows no chevron.
 */
@Composable
fun WalletChip(
    walletName: String,
    syncStatus: SyncStatus,
    canSwitch: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val switchLabel = stringResource(Res.string.shell_switch_wallet, walletName)
    val syncedLabel = stringResource(Res.string.sync_status_synced)
    val description = if (syncStatus == SyncStatus.Synced) {
        "$switchLabel, $syncedLabel"
    } else {
        switchLabel
    }

    Row(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .then(if (canSwitch) Modifier.clickable(onClick = onClick) else Modifier)
            .padding(top = 4.dp, bottom = 4.dp, end = 4.dp)
            .clearAndSetSemantics { contentDescription = description },
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        WalletAvatar(showSyncedDot = syncStatus == SyncStatus.Synced)

        Text(
            text = walletName,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurface,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f, fill = false),
        )

        if (canSwitch) {
            Icon(
                imageVector = Icons.Filled.KeyboardArrowDown,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface,
            )
        }
    }
}

/** The rounded wallet glyph, with the synced dot notched into its top-right corner. */
@Composable
internal fun WalletAvatar(
    showSyncedDot: Boolean,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier.size(36.dp)) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .align(Alignment.BottomStart)
                .background(
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = RoundedCornerShape(10.dp),
                ),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = Icons.Outlined.AccountBalanceWallet,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier.size(18.dp),
            )
        }

        if (showSyncedDot) {
            // Ringed in the bar's own colour so it reads as a badge sitting *on* the avatar
            // rather than a dot painted inside it.
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .size(14.dp)
                    .background(MaterialTheme.colorScheme.surface, CircleShape),
                contentAlignment = Alignment.Center,
            ) {
                SyncStatusDot(color = MaterialTheme.colorScheme.primary, size = 10.dp)
            }
        }
    }
}
