package com.shah.cashwise.app.shell.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.onClick
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import cashwise.composeapp.generated.resources.Res
import cashwise.composeapp.generated.resources.shell_more_options
import cashwise.composeapp.generated.resources.shell_search_transactions
import org.jetbrains.compose.resources.stringResource

/**
 * The Expanded breakpoint's top bar: a search field and the overflow menu.
 *
 * No wallet chip here — on a tablet the wallet lives in the drawer, where there is room to show
 * its type and accounts too. The wide window is spent on a real search field rather than the
 * phone's icon button.
 *
 * The field is a *button* that will open search, not a live text field: there is nothing to
 * search yet, and a focusable input that silently discards what you type is worse than an
 * obvious affordance that opens a screen.
 */
@Composable
fun ShellSearchBar(
    onSearchClick: () -> Unit,
    onMoreClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val searchLabel = stringResource(Res.string.shell_search_transactions)

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(horizontal = 24.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(
            modifier = Modifier
                .weight(1f)
                .widthIn(max = 520.dp)
                .height(52.dp)
                .background(
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    shape = RoundedCornerShape(16.dp),
                )
                .clickable(onClick = onSearchClick)
                .padding(horizontal = 16.dp)
                .clearAndSetSemantics {
                    contentDescription = searchLabel
                    role = Role.Button
                    onClick(label = searchLabel, action = null)
                },
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = Icons.Outlined.Search,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Text(
                text = searchLabel,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }

        IconButton(onClick = onMoreClick) {
            Icon(
                imageVector = Icons.Outlined.MoreVert,
                contentDescription = stringResource(Res.string.shell_more_options),
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}
