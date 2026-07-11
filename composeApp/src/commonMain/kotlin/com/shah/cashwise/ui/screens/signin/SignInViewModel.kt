package com.shah.cashwise.ui.screens.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cashwise.composeapp.generated.resources.Res
import cashwise.composeapp.generated.resources.sign_in_code_invalid
import cashwise.composeapp.generated.resources.sign_in_email_send_failed
import cashwise.composeapp.generated.resources.sign_in_error_generic
import cashwise.composeapp.generated.resources.sign_in_not_implemented
import com.shah.cashwise.domain.model.AuthSessionStatus
import com.shah.cashwise.domain.repo.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * Navigation outcome of a [SignInAction]. `null` means handled in place. Success
 * navigation is NOT returned here — Google (browser round-trip) and email OTP
 * (session set on verify) both land via the session flow, which the app shell
 * observes (see `AppNavigation`).
 */
enum class SignInNavResult {
    /** Back pressed on the Providers step — leave the sign-in flow. */
    ExitToCaller,
}

/** Drives the Sign in to Sync screen (Google + email-OTP) against [AuthRepository]. */
class SignInViewModel(
    private val authRepository: AuthRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(SignInState())
    val state: StateFlow<SignInState> = _state.asStateFlow()

    init {
        // Async sign-ins (Google browser flow, email verify) complete via the
        // session; clear any in-flight spinner once it flips to signed-in.
        viewModelScope.launch {
            authRepository.sessionStatus.collect { status ->
                if (status is AuthSessionStatus.SignedIn) {
                    _state.update { it.copy(loadingProvider = null, isSubmitting = false) }
                }
            }
        }
    }

    fun onAction(action: SignInAction): SignInNavResult? = when (action) {
        SignInAction.Back -> handleBack()

        SignInAction.ContinueWithGoogle -> {
            startGoogleSignIn()
            null
        }

        SignInAction.ContinueWithEmail -> {
            _state.update { it.copy(step = SignInStep.EmailEntry, errorMessage = null) }
            null
        }

        SignInAction.UsePhoneOtp -> {
            _state.update { it.copy(errorMessage = Res.string.sign_in_not_implemented) }
            null
        }

        SignInAction.DismissError -> {
            _state.update { it.copy(errorMessage = null) }
            null
        }

        is SignInAction.EmailChanged -> {
            _state.update { it.copy(email = action.value, errorMessage = null) }
            null
        }

        SignInAction.SendEmailCode -> {
            sendCode(advanceToCodeStep = true)
            null
        }

        is SignInAction.CodeChanged -> {
            val digits = action.value.filter(Char::isDigit).take(SignInState.CODE_LENGTH)
            _state.update { it.copy(code = digits, errorMessage = null) }
            null
        }

        SignInAction.VerifyCode -> {
            verifyCode()
            null
        }

        SignInAction.ResendCode -> {
            sendCode(advanceToCodeStep = false)
            null
        }
    }

    private fun handleBack(): SignInNavResult? = when (_state.value.step) {
        SignInStep.Providers -> SignInNavResult.ExitToCaller
        SignInStep.EmailEntry -> {
            _state.update { it.copy(step = SignInStep.Providers, errorMessage = null) }
            null
        }
        SignInStep.CodeEntry -> {
            _state.update { it.copy(step = SignInStep.EmailEntry, code = "", errorMessage = null) }
            null
        }
    }

    private fun startGoogleSignIn() {
        if (_state.value.isBusy) return
        _state.update { it.copy(loadingProvider = AuthProvider.Google, errorMessage = null) }
        viewModelScope.launch {
            val result = authRepository.signInWithGoogle()
            // The call returns once the browser is *launched*, not once sign-in
            // completes — the session arrives later via the deep link. Clear the
            // spinner either way: on success the browser now covers the app and a
            // real sign-in navigates away (AppNavigation observes the session), so
            // holding it would strand the UI in a permanent loading state if the
            // user simply cancels the browser.
            _state.update {
                it.copy(
                    loadingProvider = null,
                    errorMessage = if (result.isFailure) Res.string.sign_in_error_generic else it.errorMessage,
                )
            }
        }
    }

    private fun sendCode(advanceToCodeStep: Boolean) {
        val current = _state.value
        if (!current.isEmailValid || current.isBusy) return
        _state.update { it.copy(isSubmitting = true, errorMessage = null) }
        viewModelScope.launch {
            authRepository.sendEmailOtp(current.email.trim())
                .onSuccess {
                    _state.update {
                        it.copy(
                            isSubmitting = false,
                            step = if (advanceToCodeStep) SignInStep.CodeEntry else it.step,
                            code = if (advanceToCodeStep) "" else it.code,
                        )
                    }
                }
                .onFailure {
                    _state.update {
                        it.copy(isSubmitting = false, errorMessage = Res.string.sign_in_email_send_failed)
                    }
                }
        }
    }

    private fun verifyCode() {
        val current = _state.value
        if (!current.isCodeValid || current.isBusy) return
        _state.update { it.copy(isSubmitting = true, errorMessage = null) }
        viewModelScope.launch {
            authRepository.verifyEmailOtp(current.email.trim(), current.code)
                .onSuccess {
                    // Session flips to signed-in; collector clears submitting, app navigates.
                    _state.update { it.copy(isSubmitting = false) }
                }
                .onFailure {
                    _state.update {
                        it.copy(isSubmitting = false, errorMessage = Res.string.sign_in_code_invalid)
                    }
                }
        }
    }
}
