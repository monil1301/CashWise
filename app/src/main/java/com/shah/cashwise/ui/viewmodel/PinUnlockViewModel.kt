package com.shah.cashwise.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shah.cashwise.data.source.local.preferences.UserPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Monil on 04/06/25.
 */

@HiltViewModel
class PinUnlockViewModel @Inject constructor(private val userPreferences: UserPreferences) :
    ViewModel() {

     fun doesPinMatch(pin: String, onResult: (Boolean) -> Unit) = viewModelScope.launch {
        onResult(userPreferences.loginPin.first() == pin)
    }
}
