package com.shah.cashwise.data.source.local.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.shah.cashwise.data.model.auth.User
import com.shah.cashwise.data.model.auth.response.AuthUserResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Created by Monil on 03/06/25.
 */

class UserPreferences @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    val loginPin: Flow<String> = dataStore.data
        .map { it[PreferenceKeys.LOGIN_PIN] ?: "" }

    val firebaseToken = dataStore.data
        .map { it[PreferenceKeys.FIREBASE_TOKEN] }

    suspend fun setLoginPin(pin: String) {
        dataStore.edit { it[PreferenceKeys.LOGIN_PIN] = pin }
    }

    suspend fun setUserData(user: AuthUserResponse) {
        dataStore.edit {
            it[PreferenceKeys.FIREBASE_TOKEN] = user.firebaseUId
            it[PreferenceKeys.NAME] = user.name
            it[PreferenceKeys.EMAIL] = user.email
        }
    }

    suspend fun setFirebaseToken(token: String) {
        dataStore.edit { it[PreferenceKeys.FIREBASE_TOKEN] = token }
    }

    suspend fun setUserName(name: String) {
        dataStore.edit { it[PreferenceKeys.NAME] = name }
    }

    suspend fun setUserEmail(email: String) {
        dataStore.edit { it[PreferenceKeys.EMAIL] = email }
    }
}
