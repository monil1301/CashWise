package com.shah.cashwise.ui.screens.setup.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.shah.cashwise.domain.model.MemberRole

internal val SegmentShape = RoundedCornerShape(16.dp)

/**
 * Three-segment pill switch for picking a [MemberRole] in the
 * InviteMemberSheetContent / InviteMemberDialogContent. Selected segment
 * uses the primary fill; unselected segments sit on the tinted track.
 */
@Composable
internal fun MemberRoleSelector(
    selected: MemberRole,
    onSelect: (MemberRole) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(SegmentShape)
            .background(MaterialTheme.colorScheme.surfaceContainerHighest)
            .padding(4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        MemberRole.entries.forEach { role ->
            RoleSegment(
                role = role,
                selected = role == selected,
                onSelect = { onSelect(role) },
                modifier = Modifier.weight(1f),
            )
        }
    }
}
