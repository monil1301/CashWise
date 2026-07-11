package com.shah.cashwise.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cashwise.composeapp.generated.resources.Res
import cashwise.composeapp.generated.resources.sync_status_error
import cashwise.composeapp.generated.resources.sync_status_offline
import cashwise.composeapp.generated.resources.sync_status_syncing
import com.shah.cashwise.domain.model.SyncStatus
import com.shah.cashwise.ui.theme.CashWiseThemeTokens
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

/**
 * The top bar's sync indicator, for every state *except* [SyncStatus.Synced].
 *
 * Synced deliberately renders nothing here: it is the normal case, and a permanent "SYNCED"
 * badge would be chrome the user learns to ignore — which is exactly the badge you do not want
 * them ignoring when it later says "Offline". When synced, the only signal is a small green dot
 * on the wallet avatar (see `WalletChip`). This composable is therefore a no-op for Synced, so
 * callers can hand it the status unconditionally.
 */
@Composable
fun SyncStatusPill(
    status: SyncStatus,
    modifier: Modifier = Modifier,
) {
    val appearance = status.pillAppearance() ?: return
    val label = stringResource(appearance.label)

    Row(
        modifier = modifier
            .background(appearance.container, CircleShape)
            .padding(horizontal = 12.dp, vertical = 6.dp)
            // The dot is decoration; the label already says it. Announce the pill once.
            .clearAndSetSemantics { contentDescription = label },
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        SyncStatusDot(color = appearance.accent, size = 8.dp)
        Text(
            text = label.uppercase(),
            style = MaterialTheme.typography.labelSmall,
            color = appearance.accent,
            fontWeight = FontWeight.SemiBold,
            letterSpacing = 0.8.sp,
        )
    }
}

/** The small status dot. Also used on its own, as the "synced" badge on the wallet avatar. */
@Composable
fun SyncStatusDot(
    color: Color,
    modifier: Modifier = Modifier,
    size: Dp = 8.dp,
) {
    Box(
        modifier = modifier
            .size(size)
            .background(color, CircleShape),
    )
}

/** Container + accent + label for a pill, or `null` for [SyncStatus.Synced], which has no pill. */
@Composable
private fun SyncStatus.pillAppearance(): PillAppearance? {
    val support = CashWiseThemeTokens.supportColors
    val colors = MaterialTheme.colorScheme
    return when (this) {
        SyncStatus.Synced -> null

        SyncStatus.Syncing -> PillAppearance(
            container = colors.primaryContainer,
            accent = colors.onPrimaryContainer,
            label = Res.string.sync_status_syncing,
        )

        // Offline is not a failure — edits are still saved locally. Warning, not error.
        SyncStatus.Offline -> PillAppearance(
            container = support.warningContainer,
            accent = support.onWarningContainer,
            label = Res.string.sync_status_offline,
        )

        SyncStatus.Error -> PillAppearance(
            container = colors.errorContainer,
            accent = colors.onErrorContainer,
            label = Res.string.sync_status_error,
        )
    }
}

private data class PillAppearance(
    val container: Color,
    val accent: Color,
    val label: StringResource,
)
