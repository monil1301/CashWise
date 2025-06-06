package com.shah.cashwise.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shah.cashwise.data.source.local.preferences.UserPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Monil on 06/06/25.
 */

@HiltViewModel
class AppEntryViewModel @Inject constructor(private val userPreferences: UserPreferences): ViewModel() {
    private val _loggedInState = MutableStateFlow<Boolean?>(null)
    val loggedInState: StateFlow<Boolean?> = _loggedInState.asStateFlow()

    fun isUserLoggedIn() = viewModelScope.launch {
        _loggedInState.value = !userPreferences.firebaseToken.first().isNullOrBlank()
    }
}
