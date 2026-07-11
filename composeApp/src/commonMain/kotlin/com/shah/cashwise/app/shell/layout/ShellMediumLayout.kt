package com.shah.cashwise.app.shell.layout

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination
import com.shah.cashwise.app.shell.ShellState
import com.shah.cashwise.app.shell.ShellTab
import com.shah.cashwise.app.shell.component.ShellNavRail
import com.shah.cashwise.app.shell.component.ShellTopBar

/**
 * Medium layout (600–840dp: portrait tablet, foldable, small desktop window). The tabs move
 * off the bottom edge and onto a side rail; the top bar stays over the content.
 */
@Composable
fun ShellMediumLayout(
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
    Row(modifier = modifier.fillMaxSize()) {
        ShellNavRail(
            currentDestination = currentDestination,
            onTabClick = onTabClick,
            onAddClick = onAddClick,
        )

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = MaterialTheme.colorScheme.background,
            topBar = {
                ShellTopBar(
                    state = state,
                    onWalletClick = onWalletClick,
                    onSearchClick = onSearchClick,
                    onMoreClick = onMoreClick,
                )
            },
            content = content,
        )
    }
}
