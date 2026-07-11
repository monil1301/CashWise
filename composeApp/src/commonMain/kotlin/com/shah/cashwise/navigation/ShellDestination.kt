package com.shah.cashwise.navigation

import kotlinx.serialization.Serializable

/**
 * The five top-level tabs inside the app shell.
 *
 * These are nested *inside* [Destination.Home] rather than being siblings of it: the gates
 * (onboarding, welcome, sign-in, setup) and the tabs are different kinds of navigation. The
 * gates are a one-way sequence you leave for good; the tabs are a set you move freely between
 * and never leave. Keeping them in separate graphs means a tab switch can never land the user
 * back on the sign-in screen.
 *
 * Routes only — labels and icons are a presentation concern and live with the shell.
 */
@Serializable
sealed interface ShellDestination {

    /** Overview of the current wallet. The shell's start destination. */
    @Serializable
    data object Home : ShellDestination

    /** The running record of transactions. */
    @Serializable
    data object Transactions : ShellDestination

    /** Spending limits by category. */
    @Serializable
    data object Budgets : ShellDestination

    /** Charts and trends over the transaction history. */
    @Serializable
    data object Insights : ShellDestination

    /** Account, preferences, and app lock. */
    @Serializable
    data object Settings : ShellDestination
}
