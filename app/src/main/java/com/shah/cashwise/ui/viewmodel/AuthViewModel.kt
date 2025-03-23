package com.shah.cashwise.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.shah.cashwise.data.repository.AuthRepository
import com.shah.cashwise.utils.AuthResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val repository: AuthRepository) : ViewModel() {

    private val _authState = MutableStateFlow<AuthResult<FirebaseUser>?>(null)
    val authState = _authState.asStateFlow()

    fun registerUser(email: String, password: String) = viewModelScope.launch {
        _authState.value = AuthResult.Loading
        _authState.value = repository.registerUser(email, password)
    }

    fun loginUser(email: String, password: String) = viewModelScope.launch {
        _authState.value = AuthResult.Loading
        _authState.value = repository.loginUser(email, password)
    }

    fun signInWithGoogle(idToken: String) = viewModelScope.launch {
        _authState.value = AuthResult.Loading
        _authState.value = repository.signInWithGoogle(idToken)
    }

    fun logout() {
        repository.logout()
        _authState.value = null
    }

    fun getCurrentUser() {
        val user = repository.getCurrentUser()
        _authState.value = if (user != null) AuthResult.Success(user) else null
    }
}