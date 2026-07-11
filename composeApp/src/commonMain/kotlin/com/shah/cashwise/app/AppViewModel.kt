package com.shah.cashwise.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shah.cashwise.domain.repo.AppPreferencesRepository
import com.shah.cashwise.domain.repo.AuthRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * Resolves the app's startup destination from on-device preferences and the auth
 * session: onboarding shows only until viewed once; the welcome/sign-in gate is
 * driven by [AuthRepository.sessionStatus].
 */
class AppViewModel(
    private val appPreferencesRepository: AppPreferencesRepository,
    private val authRepository: AuthRepository,
) : ViewModel() {

    val state: StateFlow<AppState> = combine(
        appPreferencesRepository.onboardingCompleted,
        authRepository.sessionStatus,
    ) { onboardingCompleted, sessionStatus ->
        AppState(
            isLoading = false,
            onboardingCompleted = onboardingCompleted,
            sessionStatus = sessionStatus,
        )
    }.stateIn(
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

    /** Signs out and returns the user to the offline-first welcome path. */
    fun signOut() {
        viewModelScope.launch {
            authRepository.signOut()
        }
    }
}
