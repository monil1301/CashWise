package com.shah.cashwise.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import okio.Path.Companion.toPath

/** File name for the app-wide preferences store, one per platform app directory. */
internal const val DATA_STORE_FILE_NAME = "cashwise.preferences_pb"

/**
 * Builds the shared preferences [DataStore]. [producePath] supplies the absolute
 * file path, which is platform-specific (app files dir on Android, documents on
 * iOS, user home on Desktop) — see each platform's `platformModule`.
 */
internal fun createDataStore(producePath: () -> String): DataStore<Preferences> =
    PreferenceDataStoreFactory.createWithPath(
        produceFile = { producePath().toPath() },
    )
