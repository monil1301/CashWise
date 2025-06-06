package com.shah.cashwise.data.source.local.preferences

import androidx.datastore.preferences.core.stringPreferencesKey

/**
 * Created by Monil on 03/06/25.
 */

object PreferenceKeys {

        // User
        val LOGIN_PIN = stringPreferencesKey("login_pin")
        val NAME = stringPreferencesKey("name")
        val EMAIL = stringPreferencesKey("email")
        val FIREBASE_TOKEN = stringPreferencesKey("firebase_token")
}
