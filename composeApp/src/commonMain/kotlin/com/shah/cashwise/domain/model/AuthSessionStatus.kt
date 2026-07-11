package com.shah.cashwise.domain.model

/**
 * Domain view of the auth session, observed by the app shell to gate the
 * sign-in path. Offline-first: [SignedOut] is a fully usable state, not an error.
 */
sealed interface AuthSessionStatus {
    /** The session is still being restored from storage — destination unknown. */
    data object Loading : AuthSessionStatus

    /** A valid session exists. */
    data class SignedIn(val user: AuthUser) : AuthSessionStatus

    /** No session (never signed in, signed out, or refresh failed). */
    data object SignedOut : AuthSessionStatus
}
