package com.shah.cashwise.utils

sealed class AuthResult<out T> {
    data class Success<out T>(val data: T): AuthResult<T>()
    data class Failure(val message: String): AuthResult<Nothing>()
    object Loading: AuthResult<Nothing>()
}