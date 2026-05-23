package com.shah.cashwise.ui.screens.setup.illustration

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountBalanceWallet
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

/**
 * Decorative artwork for the "Invite members" step shown on the brand panel
 * of SetupExpandedLayout — a wallet card with three person avatars
 * positioned around it to suggest a shared wallet. Mirrors the visual weight
 * of [SetupWalletIllustration].
 */
@Composable
internal fun SetupInviteIllustration(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .size(280.dp)
            .clip(RoundedCornerShape(32.dp))
            .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.35f)),
        contentAlignment = Alignment.Center,
    ) {
        Box(
            modifier = Modifier
                .size(150.dp)
                .clip(RoundedCornerShape(28.dp))
                .background(MaterialTheme.colorScheme.primary),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = Icons.Outlined.AccountBalanceWallet,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.size(64.dp),
            )
        }

        PersonBadge(
            modifier = Modifier
                .align(Alignment.TopStart)
                .offset(x = 32.dp, y = 32.dp)
                .size(48.dp),
        )

        PersonBadge(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .offset(x = (-24).dp, y = 56.dp)
                .size(40.dp),
        )

        PersonBadge(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .offset(x = (-32).dp, y = (-32).dp)
                .size(52.dp),
        )
    }
}
