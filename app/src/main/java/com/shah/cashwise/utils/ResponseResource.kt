package com.shah.cashwise.utils

/**
 * Created by Monil on 17/05/25.
 */

sealed class ResponseResource<out T> {

    data class Success<out T>(
        val data: T
    ) : ResponseResource<T>()

    data class Failure(
        val isNetworkError: Boolean = false,
        val errorMessage: String = "An unknown error occurred",
        val statusCode: Int? = null
    ) : ResponseResource<Nothing>()

    object Loading: ResponseResource<Nothing>()
}