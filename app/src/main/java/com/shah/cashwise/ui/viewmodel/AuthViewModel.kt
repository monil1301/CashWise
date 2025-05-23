package com.shah.cashwise.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shah.cashwise.data.model.auth.FirebaseUserSignUp
import com.shah.cashwise.data.model.auth.User
import com.shah.cashwise.data.model.auth.response.AuthUserResponse
import com.shah.cashwise.data.repository.AuthRepository
import com.shah.cashwise.utils.ResponseResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Monil on 20/04/25.
 */

@HiltViewModel
class AuthViewModel @Inject constructor(private val repository: AuthRepository) : ViewModel() {

    private val _authState = MutableStateFlow<ResponseResource<AuthUserResponse>?>(null)
    val authState = _authState.asStateFlow()

    fun registerUser(user: FirebaseUserSignUp) = viewModelScope.launch {
        _authState.value = ResponseResource.Loading
        val firebaseResponse = repository.registerUser(user)

        if (firebaseResponse is ResponseResource.Success) {
            getCurrentUser()?.uid?.let {
                _authState.value = repository.registerUserWithBackend(
                    User(user.email, it, user.name)
                )
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

    fun loginUser(user: FirebaseUserSignUp) = viewModelScope.launch {
        _authState.value = ResponseResource.Loading
        val firebaseResponse = repository.loginUser(user)

        if (firebaseResponse is ResponseResource.Success) {
            getCurrentUser()?.uid?.let {
                _authState.value = repository.loginUserWithBackend(
                    User(user.email, it, user.name)
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