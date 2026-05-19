package com.shah.cashwise.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.shah.cashwise.data.local.DATA_STORE_FILE_NAME
import com.shah.cashwise.data.local.createDataStore
import org.koin.core.module.Module
import org.koin.dsl.module
import java.io.File

actual val platformModule: Module = module {
    single<DataStore<Preferences>> {
        createDataStore {
            val appDir = File(System.getProperty("user.home"), ".cashwise").apply { mkdirs() }
            File(appDir, DATA_STORE_FILE_NAME).absolutePath
        }
    }
}
