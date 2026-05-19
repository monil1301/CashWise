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
}
