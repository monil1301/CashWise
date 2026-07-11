package com.shah.cashwise.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.russhwolf.settings.ExperimentalSettingsImplementation
import com.russhwolf.settings.KeychainSettings

/**
 * Keychain-backed store. Uses the library's [KeychainSettings] rather than hand-written
 * cinterop — Keychain's CoreFoundation bridging has ownership rules that are very easy to get
 * subtly wrong, and this is the one place a subtle bug is unacceptable.
 *
 * The [dataStore] argument is ignored: on iOS the Keychain is strictly better, since it
 * survives reinstalls and is protected by the Secure Enclave-backed device key.
 */
@OptIn(ExperimentalSettingsImplementation::class)
private class KeychainSecureStore : SecureStore {

    private val settings = KeychainSettings(service = "com.shah.cashwise.securestore")

    override suspend fun put(key: String, value: String) = settings.putString(key, value)
    override suspend fun get(key: String): String? = settings.getStringOrNull(key)
    override suspend fun remove(key: String) = settings.remove(key)
}

actual fun createSecureStore(dataStore: DataStore<Preferences>): SecureStore = KeychainSecureStore()
