package com.shah.cashwise.app.shell.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cashwise.composeapp.generated.resources.Res
import cashwise.composeapp.generated.resources.shell_more_options
import cashwise.composeapp.generated.resources.shell_search
import com.shah.cashwise.app.shell.ShellState
import com.shah.cashwise.ui.components.SyncStatusPill
import org.jetbrains.compose.resources.stringResource

/**
 * The shell's top bar: the current wallet on the left, sync + actions on the right.
 *
 * Identical across every breakpoint — only the navigation moves between the bottom and the
 * side, so this is shared rather than duplicated per layout.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShellTopBar(
    state: ShellState,
    onWalletClick: () -> Unit,
    onSearchClick: () -> Unit,
    onMoreClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    TopAppBar(
        modifier = modifier,
        title = {
            val wallet = state.selectedWallet ?: return@TopAppBar
            WalletChip(
                walletName = wallet.name,
                syncStatus = state.syncStatus,
                canSwitch = state.canSwitchWallet,
                onClick = onWalletClick,
            )
        },
        actions = {
            // Renders nothing while synced — the green dot on the wallet avatar carries that.
            SyncStatusPill(
                status = state.syncStatus,
                modifier = Modifier.padding(end = 4.dp),
            )

            IconButton(onClick = onSearchClick) {
                Icon(
                    imageVector = Icons.Outlined.Search,
                    contentDescription = stringResource(Res.string.shell_search),
                )
            }

            IconButton(onClick = onMoreClick) {
                Icon(
                    imageVector = Icons.Outlined.MoreVert,
                    contentDescription = stringResource(Res.string.shell_more_options),
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface,
            titleContentColor = MaterialTheme.colorScheme.onSurface,
            actionIconContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
        ),
    )
}
