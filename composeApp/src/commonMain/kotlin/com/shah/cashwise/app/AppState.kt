package com.shah.cashwise.app

/**
 * Startup state for [App]. [isLoading] stays true until the persisted
 * preferences have been read, so the UI never flashes onboarding before the
 * real destination is known.
 */
data class AppState(
    val isLoading: Boolean = true,
    val onboardingCompleted: Boolean = false,
)
