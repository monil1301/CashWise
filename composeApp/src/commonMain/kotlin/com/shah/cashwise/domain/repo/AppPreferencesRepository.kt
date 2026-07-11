package com.shah.cashwise.domain.repo

import kotlinx.coroutines.flow.Flow

/**
 * App-wide user preferences persisted on-device only — no account, no sync.
 */
interface AppPreferencesRepository {

    /** Emits whether the user has already seen the onboarding intro. */
    val onboardingCompleted: Flow<Boolean>

    /** Records that the onboarding intro has been viewed (or skipped). */
    suspend fun setOnboardingCompleted(completed: Boolean)

    // NOTE: there is deliberately no `setupCompleted` flag here. Setup completion is
    // derived from the database (`WalletRepository.hasWallet`) — the wallet *is* the
    // artefact setup produces, so one durable source of truth cannot disagree with itself.

    /**
     * Emits whether the user asked for the app to be PIN-locked during setup.
     *
     * Only the *preference* is stored. The PIN itself is deliberately NOT persisted
     * yet: a 4–6 digit PIN has a keyspace small enough to brute-force instantly, so
     * a hash in DataStore would be false comfort. It needs the platform Keystore /
     * Keychain and a slow KDF — tracked as the app-lock follow-up. Nothing is lost
     * today because lock enforcement (a lock screen on launch) is not implemented.
     */
    val appLockEnabled: Flow<Boolean>

    /** Records whether the user opted into the app lock. */
    suspend fun setAppLockEnabled(enabled: Boolean)
}
