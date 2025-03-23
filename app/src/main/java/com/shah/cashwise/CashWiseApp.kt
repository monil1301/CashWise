package com.shah.cashwise

import android.app.Application
import com.google.firebase.FirebaseApp
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CashWiseApp: Application() {

    override fun onCreate() {
        super.onCreate()

        // Initialise Firebase
        FirebaseApp.initializeApp(this)
    }
}
