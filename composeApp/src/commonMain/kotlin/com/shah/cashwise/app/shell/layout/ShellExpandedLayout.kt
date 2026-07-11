package com.shah.cashwise.app.shell.layout

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import cashwise.composeapp.generated.resources.Res
import cashwise.composeapp.generated.resources.shell_detail_pane_placeholder
import com.shah.cashwise.app.shell.ShellState
import com.shah.cashwise.app.shell.ShellTab
import com.shah.cashwise.app.shell.component.AppDrawer
import com.shah.cashwise.app.shell.component.ShellSearchBar
import org.jetbrains.compose.resources.stringResource

/**
 * Below this, two panes would each be too narrow to be worth having, so the primary pane takes
 * the full width instead. Landscape tablets clear it; a small desktop window may not.
 */
private val TwoPaneMinWidth = 900.dp

/**
 * Expanded layout (≥840dp: landscape tablet, desktop).
 *
 * A permanent drawer replaces the phone's bottom bar, and the content area splits into a primary
 * pane and an optional detail pane. Same destinations as the phone, same order — only the chrome
 * moves.
 */
@Composable
fun ShellExpandedLayout(
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
    Row(
        modifier = modifier
            .fillMaxSize()
            // No Scaffold here, so insets are handled once at the root rather than by bars.
            .windowInsetsPadding(WindowInsets.safeDrawing),
    ) {
        AppDrawer(
            state = state,
            currentDestination = currentDestination,
            onTabClick = onTabClick,
            onWalletClick = onWalletClick,
            onAddClick = onAddClick,
        )

        VerticalDivider(
            modifier = Modifier.fillMaxHeight(),
            color = MaterialTheme.colorScheme.outlineVariant,
        )

        Column(modifier = Modifier.fillMaxSize()) {
            ShellSearchBar(
                onSearchClick = onSearchClick,
                onMoreClick = onMoreClick,
            )

            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)

            BoxWithConstraints(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
            ) {
                if (maxWidth >= TwoPaneMinWidth) {
                    Row(modifier = Modifier.fillMaxSize()) {
                        Box(modifier = Modifier.weight(1f).fillMaxHeight()) {
                            content(PaddingValues(0.dp))
                        }
                        DetailPane(modifier = Modifier.weight(1f).fillMaxHeight())
                    }
                } else {
                    content(PaddingValues(0.dp))
                }
            }
        }
    }
}

/**
 * The optional second pane.
 *
 * A placeholder for now: showing a detail means a feature has something selected to show, and no
 * tab has selection yet.
 *
 * TODO(shell): let a tab supply its own detail content — most likely a nullable slot the tab's
 *  screen provides when it has a selected item, so the pane collapses when nothing is selected.
 */
@Composable
private fun DetailPane(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.padding(start = 16.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = stringResource(Res.string.shell_detail_pane_placeholder),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
        )
    }
}
