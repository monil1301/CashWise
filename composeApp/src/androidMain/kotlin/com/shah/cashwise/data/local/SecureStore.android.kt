package com.shah.cashwise.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

/** No OS secret store here — app-private storage plus the PBKDF2 verifier. */
actual fun createSecureStore(dataStore: DataStore<Preferences>): SecureStore =
    DataStoreSecureStore(dataStore)
