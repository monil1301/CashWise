package com.shah.cashwise.ui.screens.setup.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Row in the InviteMembersStep members list — circular tinted avatar with a
 * person glyph on the leading edge, the member [name], and a tinted [role]
 * pill on the trailing edge.
 */
@Composable
internal fun MemberRow(
    name: String,
    role: String,
    modifier: Modifier = Modifier,
) {
    OutlinedCard(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            MemberAvatar()

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = name,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.weight(1f),
            )

            RoleBadge(role = role)
        }
    }
}
