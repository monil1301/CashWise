package com.shah.cashwise.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shah.cashwise.data.source.local.preferences.UserPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Monil on 06/06/25.
 */

@HiltViewModel
class PinSetupViewModel @Inject constructor(private val userPreferences: UserPreferences) :
    ViewModel() {

    fun saveUnlockPin(pin: String) = viewModelScope.launch {
        userPreferences.setLoginPin(pin)
    }
}
