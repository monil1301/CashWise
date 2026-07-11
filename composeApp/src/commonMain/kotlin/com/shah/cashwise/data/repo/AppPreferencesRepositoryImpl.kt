package com.shah.cashwise.data.repo

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.shah.cashwise.domain.repo.AppPreferencesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/** [AppPreferencesRepository] backed by a Preferences [DataStore]. */
class AppPreferencesRepositoryImpl(
    private val dataStore: DataStore<Preferences>,
) : AppPreferencesRepository {

    override val onboardingCompleted: Flow<Boolean> =
        dataStore.data.map { preferences -> preferences[OnboardingCompletedKey] ?: false }

    override suspend fun setOnboardingCompleted(completed: Boolean) {
        dataStore.edit { preferences -> preferences[OnboardingCompletedKey] = completed }
    }

    override val appLockEnabled: Flow<Boolean> =
        dataStore.data.map { preferences -> preferences[AppLockEnabledKey] ?: false }

    override suspend fun setAppLockEnabled(enabled: Boolean) {
        dataStore.edit { preferences -> preferences[AppLockEnabledKey] = enabled }
    }

    private companion object {
        val OnboardingCompletedKey = booleanPreferencesKey("onboarding_completed")
        val AppLockEnabledKey = booleanPreferencesKey("app_lock_enabled")
    }
}
