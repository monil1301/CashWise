package com.shah.cashwise.app.shell

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ReceiptLong
import androidx.compose.material.icons.automirrored.outlined.ReceiptLong
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PieChart
import androidx.compose.material.icons.filled.QueryStats
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.PieChart
import androidx.compose.material.icons.outlined.QueryStats
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import cashwise.composeapp.generated.resources.Res
import cashwise.composeapp.generated.resources.shell_tab_budgets
import cashwise.composeapp.generated.resources.shell_tab_home
import cashwise.composeapp.generated.resources.shell_tab_insights
import cashwise.composeapp.generated.resources.shell_tab_settings
import cashwise.composeapp.generated.resources.shell_tab_transactions
import com.shah.cashwise.navigation.ShellDestination
import org.jetbrains.compose.resources.StringResource
import kotlin.reflect.KClass

/**
 * The presentation half of a [ShellDestination]: what it is called and what it looks like.
 *
 * Kept out of `navigation/` so routes stay free of Compose types, and declared once here so the
 * phone's bottom bar and the tablet's drawer cannot drift apart — both render [ShellTab.entries],
 * in this order, with these labels and icons.
 *
 * The selected/unselected icon pair follows the Material convention: filled when active,
 * outlined when not.
 */
enum class ShellTab(
    val destination: ShellDestination,
    val route: KClass<out ShellDestination>,
    val label: StringResource,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
) {
    Home(
        destination = ShellDestination.Home,
        route = ShellDestination.Home::class,
        label = Res.string.shell_tab_home,
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home,
    ),
    Transactions(
        destination = ShellDestination.Transactions,
        route = ShellDestination.Transactions::class,
        label = Res.string.shell_tab_transactions,
        selectedIcon = Icons.AutoMirrored.Filled.ReceiptLong,
        unselectedIcon = Icons.AutoMirrored.Outlined.ReceiptLong,
    ),
    Budgets(
        destination = ShellDestination.Budgets,
        route = ShellDestination.Budgets::class,
        label = Res.string.shell_tab_budgets,
        selectedIcon = Icons.Filled.PieChart,
        unselectedIcon = Icons.Outlined.PieChart,
    ),
    Insights(
        destination = ShellDestination.Insights,
        route = ShellDestination.Insights::class,
        label = Res.string.shell_tab_insights,
        selectedIcon = Icons.Filled.QueryStats,
        unselectedIcon = Icons.Outlined.QueryStats,
    ),
    Settings(
        destination = ShellDestination.Settings,
        route = ShellDestination.Settings::class,
        label = Res.string.shell_tab_settings,
        selectedIcon = Icons.Filled.Settings,
        unselectedIcon = Icons.Outlined.Settings,
    ),
}

/**
 * Whether this tab is the one currently shown.
 *
 * Walks the destination's parents, not just the destination itself, so a tab stays visibly
 * selected when the user drills into a sub-screen within it.
 */
fun ShellTab.isSelected(current: NavDestination?): Boolean =
    current?.hierarchy?.any { it.hasRoute(route) } == true
