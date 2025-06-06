package com.shah.cashwise.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shah.cashwise.data.model.auth.FirebaseUserSignUp
import com.shah.cashwise.data.model.auth.LoginFormState
import com.shah.cashwise.data.model.auth.SignUpFormState
import com.shah.cashwise.data.model.auth.User
import com.shah.cashwise.data.model.auth.response.AuthUserResponse
import com.shah.cashwise.data.repository.AuthRepository
import com.shah.cashwise.data.source.local.preferences.UserPreferences
import com.shah.cashwise.utils.ResponseResource
import com.shah.cashwise.utils.extensions.isValidEmail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Monil on 20/04/25.
 */

@HiltViewModel
class AuthViewModel @Inject constructor(private val repository: AuthRepository, private val userPreferences: UserPreferences) : ViewModel() {

    var loginFormState by mutableStateOf(LoginFormState())
        private set

    var signUpFormState by mutableStateOf(SignUpFormState())
        private set

    private val _authState = MutableStateFlow<ResponseResource<AuthUserResponse>?>(null)
    val authState: StateFlow<ResponseResource<AuthUserResponse>?> = _authState.asStateFlow()

    var isRegistering by mutableStateOf(true)

    fun onNameChanged(name: String) {
        if (isRegistering)
            signUpFormState = signUpFormState.copy(name = name, nameError = null)
    }

    fun onEmailChanged(email: String) {
        if (isRegistering)
            signUpFormState = signUpFormState.copy(email = email, emailError = null)
        else
            loginFormState = loginFormState.copy(email = email, emailError = null)
    }

    fun onPasswordChanged(password: String) {
        if (isRegistering)
            signUpFormState = signUpFormState.copy(password = password, passwordError = null)
        else
            loginFormState = loginFormState.copy(password = password, passwordError = null)
    }

    fun validateAndLogin() {
        val current = loginFormState
        var hasError = false

        val emailError = if (!current.email.isValidEmail()) {
            hasError = true
            "Invalid email format"
        } else null

        val passwordError = if (current.password.length < 6) {
            hasError = true
            "Password must be at least 6 characters"
        } else null

        loginFormState = current.copy(
            emailError = emailError,
            passwordError = passwordError
        )

        if (!hasError) {
            loginUser(FirebaseUserSignUp(email = current.email, password = current.password))
        }
    }

    fun validateAndRegister() {
        val current = signUpFormState
        var hasError = false

        val nameError = if (current.name.length < 2) {
            hasError = true
            "Name must be at least 2 characters"
        } else null

        val emailError = if (!current.email.isValidEmail()) {
            hasError = true
            "Invalid email format"
        } else null

        val passwordError = if (current.password.length < 6) {
            hasError = true
            "Password must be at least 6 characters"
        } else null

        signUpFormState = current.copy(
            nameError = nameError,
            emailError = emailError,
            passwordError = passwordError
        )

        if (!hasError) {
            registerUser(FirebaseUserSignUp(current.name, current.email, current.password))
        }
    }

    private fun registerUser(user: FirebaseUserSignUp) = viewModelScope.launch {
        _authState.value = ResponseResource.Loading
        val firebaseResponse = repository.registerUser(user)

        if (firebaseResponse is ResponseResource.Success) {
            getCurrentUser()?.uid?.let {
                _authState.value = repository.registerUserWithBackend(
                    User(user.email, it, user.name)
                )

                if (_authState.value is ResponseResource.Success) {
                    userPreferences.setUserData((_authState.value as ResponseResource.Success<AuthUserResponse>).data)
                }
            } ?: {
                _authState.value = ResponseResource.Failure()
            }
        } else if (firebaseResponse is ResponseResource.Failure) {
            Log.d("Firebase", " ${firebaseResponse.errorMessage}")
            _authState.value = ResponseResource.Failure(
                firebaseResponse.isNetworkError,
                firebaseResponse.errorMessage
            )
        }
    }

    private fun loginUser(user: FirebaseUserSignUp) = viewModelScope.launch {
        _authState.value = ResponseResource.Loading
        val firebaseResponse = repository.loginUser(user)

        if (firebaseResponse is ResponseResource.Success) {
            getCurrentUser()?.uid?.let {
                _authState.value = repository.loginUserWithBackend(
                    User(user.email, it, user.name)
                )

                if (_authState.value is ResponseResource.Success) {
                    userPreferences.setUserData((_authState.value as ResponseResource.Success<AuthUserResponse>).data)
                }
            } ?: {
                _authState.value = ResponseResource.Failure()
            }
        } else if (firebaseResponse is ResponseResource.Failure) {
            _authState.value = ResponseResource.Failure(
                firebaseResponse.isNetworkError,
                firebaseResponse.errorMessage
            )
        }
    }

    fun signInWithGoogle(idToken: String) = viewModelScope.launch {
        _authState.value = ResponseResource.Loading
        val firebaseResponse = repository.signInWithGoogle(idToken)

        if (firebaseResponse is ResponseResource.Success) {
            val user = firebaseResponse.data
            getCurrentUser()?.uid?.let {
                repository.registerUserWithBackend(
                    User(user.email!!, it, user.displayName!!)
                )
            } ?: {
                _authState.value = ResponseResource.Failure()
            }
        } else if (firebaseResponse is ResponseResource.Failure) {
            _authState.value = ResponseResource.Failure(
                firebaseResponse.isNetworkError,
                firebaseResponse.errorMessage
            )
        }
    }

    fun logout() {
        repository.logout()
        _authState.value = null
    }

    fun getCurrentUser() = repository.getCurrentUser()

}