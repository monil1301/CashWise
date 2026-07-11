package com.shah.cashwise.app

import com.shah.cashwise.domain.model.AuthSessionStatus

/**
 * Startup state for [App]. [isLoading] stays true until the persisted
 * preferences have been read, so the UI never flashes onboarding before the
 * real destination is known. [sessionStatus] gates the optional sign-in path;
 * [Loading][AuthSessionStatus.Loading] means the session is still being restored.
 */
data class AppState(
    val isLoading: Boolean = true,
    val onboardingCompleted: Boolean = false,
    val sessionStatus: AuthSessionStatus = AuthSessionStatus.Loading,
) {
    /** True once a valid auth session exists. */
    val isSignedIn: Boolean get() = sessionStatus is AuthSessionStatus.SignedIn

    /** True while the session is still being restored from storage. */
    val isSessionResolving: Boolean get() = sessionStatus is AuthSessionStatus.Loading
}
