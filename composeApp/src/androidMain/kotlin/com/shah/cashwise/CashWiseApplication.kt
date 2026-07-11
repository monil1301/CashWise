package com.shah.cashwise

import android.app.Application
import android.content.Context
import com.shah.cashwise.di.initKoin
import org.koin.core.context.GlobalContext

/**
 * Application entry point. Holds an application [Context] so the DataStore-backed
 * preferences store can resolve the app files directory without threading a
 * Context through Koin. Registered via `android:name` in the manifest.
 *
 * Also starts the global Koin container so both Compose and platform code (e.g.
 * the OAuth deep-link handler in [MainActivity]) share one container.
 */
class CashWiseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        if (GlobalContext.getOrNull() == null) {
            initKoin()
        }
    }

    companion object {
        private lateinit var instance: CashWiseApplication

        val appContext: Context
            get() = instance.applicationContext
    }
}
