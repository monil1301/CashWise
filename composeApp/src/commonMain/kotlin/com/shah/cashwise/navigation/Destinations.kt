package com.shah.cashwise.navigation

import kotlinx.serialization.Serializable

/**
 * Every top-level destination in the app, as type-safe routes.
 *
 * These are objects rather than route strings so a destination can never be mistyped and
 * arguments stay compiler-checked — screens navigate via these, never via raw strings.
 *
 * The pre-app gates ([Onboarding], [Welcome], [SignIn], [Setup]) are only reachable until
 * setup produces a wallet; from then on [Home] is the start destination. See `AppNavGraph`.
 */
sealed interface Destination {

    /** First-launch intro carousel. Shown once, then never again. */
    @Serializable
    data object Onboarding : Destination

    /** Offline-first entry point: "Sign in to sync" or "Continue offline". */
    @Serializable
    data object Welcome : Destination

    /** Optional Google / email-OTP sign-in. Not required to use the app. */
    @Serializable
    data object SignIn : Destination

    /** Multi-step first-run setup. Completing it writes the first wallet. */
    @Serializable
    data object Setup : Destination

    /** The app proper, once a wallet exists. The tabbed shell will live here. */
    @Serializable
    data object Home : Destination
}
