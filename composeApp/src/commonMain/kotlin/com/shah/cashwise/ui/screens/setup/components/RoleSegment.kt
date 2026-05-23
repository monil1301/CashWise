package com.shah.cashwise.ui.screens.setup.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.shah.cashwise.domain.model.MemberRole
import com.shah.cashwise.ui.screens.setup.model.nameRes
import org.jetbrains.compose.resources.stringResource

/** A single selectable segment of the [MemberRoleSelector] pill. */
@Composable
internal fun RoleSegment(
    role: MemberRole,
    selected: Boolean,
    onSelect: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val backgroundColor = if (selected) {
        MaterialTheme.colorScheme.primary
    } else {
        Color.Transparent
    }
    val contentColor = if (selected) {
        MaterialTheme.colorScheme.onPrimary
    } else {
        MaterialTheme.colorScheme.onSurfaceVariant
    }

    Box(
        modifier = modifier
            .clip(SegmentShape)
            .background(backgroundColor)
            .selectable(selected = selected, onClick = onSelect)
            .padding(vertical = 12.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = stringResource(role.nameRes()),
            style = MaterialTheme.typography.titleSmall,
            color = contentColor,
        )
    }
}
