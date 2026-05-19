package com.shah.cashwise

import android.app.Application
import android.content.Context

/**
 * Application entry point. Holds an application [Context] so the DataStore-backed
 * preferences store can resolve the app files directory without threading a
 * Context through Koin. Registered via `android:name` in the manifest.
 */
class CashWiseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        private lateinit var instance: CashWiseApplication

        val appContext: Context
            get() = instance.applicationContext
    }
}
