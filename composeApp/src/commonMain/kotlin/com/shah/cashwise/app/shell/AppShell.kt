package com.shah.cashwise.app.shell

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.shah.cashwise.app.shell.component.WalletPickerSheet
import com.shah.cashwise.app.shell.layout.ShellCompactLayout
import com.shah.cashwise.app.shell.layout.ShellExpandedLayout
import com.shah.cashwise.app.shell.layout.ShellMediumLayout
import com.shah.cashwise.navigation.ShellDestination
import com.shah.cashwise.ui.screens.budgets.BudgetsScreen
import com.shah.cashwise.ui.screens.home.HomeScreen
import com.shah.cashwise.ui.screens.insights.InsightsScreen
import com.shah.cashwise.ui.screens.settings.SettingsScreen
import com.shah.cashwise.ui.screens.transactions.TransactionsScreen
import org.koin.compose.viewmodel.koinViewModel

private enum class ShellLayoutType { Compact, Medium, Expanded }

/**
 * The app proper, once a wallet exists: persistent chrome (top bar, tabs, add button) wrapped
 * around a tab-local [NavHost].
 *
 * The tabs get their **own** NavHost rather than joining the outer graph. The outer graph is a
 * sequence of gates you pass through once and never return to; the tabs are a set you live in.
 * Nesting them keeps a tab switch from ever popping the user back onto the sign-in screen, and
 * lets each tab keep its own back stack.
 */
@Composable
fun AppShell(modifier: Modifier = Modifier) {
    val viewModel = koinViewModel<ShellViewModel>()
    val state by viewModel.state.collectAsState()
    val navController = rememberNavController()
    val currentDestination by navController.currentBackStackEntryAsState()

    // TODO(shell): search and the overflow menu are chrome the mock calls for but nothing is
    //  behind them yet. They land with the Journal feature.
    val onSearchClick = {}
    val onMoreClick = {}
    val onAddClick = {}

    BoxWithConstraints(modifier = modifier.fillMaxSize()) {
        val layoutType = when {
            maxWidth < 600.dp -> ShellLayoutType.Compact
            maxWidth < 840.dp -> ShellLayoutType.Medium
            else -> ShellLayoutType.Expanded
        }

        val content: @Composable (PaddingValues) -> Unit = { padding ->
            ShellNavHost(
                navController = navController,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
            )
        }

        when (layoutType) {
            ShellLayoutType.Compact -> ShellCompactLayout(
                state = state,
                currentDestination = currentDestination?.destination,
                onTabClick = { navController.switchTab(it) },
                onWalletClick = { viewModel.onAction(ShellAction.WalletChipClicked) },
                onSearchClick = onSearchClick,
                onMoreClick = onMoreClick,
                onAddClick = onAddClick,
                content = content,
            )

            ShellLayoutType.Medium -> ShellMediumLayout(
                state = state,
                currentDestination = currentDestination?.destination,
                onTabClick = { navController.switchTab(it) },
                onWalletClick = { viewModel.onAction(ShellAction.WalletChipClicked) },
                onSearchClick = onSearchClick,
                onMoreClick = onMoreClick,
                onAddClick = onAddClick,
                content = content,
            )

            ShellLayoutType.Expanded -> ShellExpandedLayout(
                state = state,
                currentDestination = currentDestination?.destination,
                onTabClick = { navController.switchTab(it) },
                onWalletClick = { viewModel.onAction(ShellAction.WalletChipClicked) },
                onSearchClick = onSearchClick,
                onMoreClick = onMoreClick,
                onAddClick = onAddClick,
                content = content,
            )
        }

        if (state.showWalletPicker) {
            WalletPickerSheet(
                wallets = state.wallets,
                selectedWalletId = state.selectedWallet?.id,
                onWalletSelected = { viewModel.onAction(ShellAction.WalletSelected(it)) },
                onDismiss = { viewModel.onAction(ShellAction.WalletPickerDismissed) },
            )
        }
    }
}

/** The tab graph. Home is the start destination, so Back from any tab returns to it. */
@Composable
private fun ShellNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = ShellDestination.Home,
        modifier = modifier,
    ) {
        composable<ShellDestination.Home> { HomeScreen() }
        composable<ShellDestination.Transactions> { TransactionsScreen() }
        composable<ShellDestination.Budgets> { BudgetsScreen() }
        composable<ShellDestination.Insights> { InsightsScreen() }
        composable<ShellDestination.Settings> { SettingsScreen() }
    }
}

/**
 * Standard tab-switch semantics: one entry per tab on the stack, each tab's scroll position and
 * inner back stack preserved across switches, and Back from any tab returning to Home rather
 * than walking every tab the user has ever visited.
 */
private fun NavHostController.switchTab(tab: ShellTab) {
    navigate(tab.destination) {
        popUpTo(graph.findStartDestination().id) { saveState = true }
        launchSingleTop = true
        restoreState = true
    }
}
