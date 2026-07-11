package com.shah.cashwise.app.shell.layout

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination
import cashwise.composeapp.generated.resources.Res
import cashwise.composeapp.generated.resources.shell_add_transaction
import com.shah.cashwise.app.shell.ShellState
import com.shah.cashwise.app.shell.ShellTab
import com.shah.cashwise.app.shell.component.ShellBottomBar
import com.shah.cashwise.app.shell.component.ShellTopBar
import org.jetbrains.compose.resources.stringResource

/**
 * Phone layout: top bar, bottom tabs, and a FAB in the bottom-right.
 *
 * [content] receives the scaffold's insets so screens can pad themselves — the shell does not
 * pad on their behalf, since a scrolling list wants the padding on its content, not on its
 * viewport.
 */
@Composable
fun ShellCompactLayout(
    state: ShellState,
    currentDestination: NavDestination?,
    onTabClick: (ShellTab) -> Unit,
    onWalletClick: () -> Unit,
    onSearchClick: () -> Unit,
    onMoreClick: () -> Unit,
    onAddClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable (PaddingValues) -> Unit,
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            ShellTopBar(
                state = state,
                onWalletClick = onWalletClick,
                onSearchClick = onSearchClick,
                onMoreClick = onMoreClick,
            )
        },
        bottomBar = {
            ShellBottomBar(
                currentDestination = currentDestination,
                onTabClick = onTabClick,
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddClick,
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = stringResource(Res.string.shell_add_transaction),
                )
            }
        },
        content = content,
    )
}
