package com.shah.cashwise.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first

/**
 * Key/value store for secrets, backed by the platform's most protected storage.
 *
 * Kept separate from [AppPreferencesRepository]'s DataStore on purpose: ordinary preferences
 * live in a plain file, which is fine for "has onboarding been seen" and emphatically not
 * fine for an app-lock verifier.
 */
interface SecureStore {
    suspend fun put(key: String, value: String)
    suspend fun get(key: String): String?
    suspend fun remove(key: String)
}

/**
 * Fallback for platforms with no OS-level secret store (Android, Desktop). The file is
 * app-private, so on Android the sandbox and full-disk encryption protect it — provided
 * backups are off (`allowBackup=false`), or `adb backup` would hand it straight over.
 *
 * On Desktop there is no protected store at all: anything here is readable by the user's own
 * account. That is an accepted limitation, which is why the stored value is a slow PBKDF2
 * verifier rather than anything directly reusable.
 */
internal class DataStoreSecureStore(
    private val dataStore: DataStore<Preferences>,
) : SecureStore {

    override suspend fun put(key: String, value: String) {
        dataStore.edit { it[stringPreferencesKey(key)] = value }
    }

    override suspend fun get(key: String): String? =
        dataStore.data.first()[stringPreferencesKey(key)]

    override suspend fun remove(key: String) {
        dataStore.edit { it.remove(stringPreferencesKey(key)) }
    }
}

/**
 * The platform [SecureStore]. iOS gets the real Keychain; Android and Desktop fall back to
 * app-private storage (see [DataStoreSecureStore]).
 */
expect fun createSecureStore(dataStore: DataStore<Preferences>): SecureStore
