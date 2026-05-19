package com.shah.cashwise.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shah.cashwise.domain.repo.AppPreferencesRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * Resolves the app's startup destination from on-device preferences: onboarding
 * is shown only until it has been viewed once.
 */
class AppViewModel(
    private val appPreferencesRepository: AppPreferencesRepository,
) : ViewModel() {

    val state: StateFlow<AppState> = appPreferencesRepository.onboardingCompleted
        .map { completed -> AppState(isLoading = false, onboardingCompleted = completed) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = AppState(),
        )

    /** Persists that onboarding has been viewed so it is skipped on next launch. */
    fun onOnboardingFinished() {
        viewModelScope.launch {
            appPreferencesRepository.setOnboardingCompleted(true)
        }
    }
}
