package com.shah.cashwise.data.repository

import com.shah.cashwise.utils.ResponseResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

/**
 * Created by Monil on 17/05/25.
 */

abstract class BaseRepository {

    suspend fun <T> safeApiCall(apiCall: suspend () -> T): ResponseResource<T> {
        return withContext(Dispatchers.IO) {
            try {
                val result = apiCall()
                ResponseResource.Success(result)
            } catch (e: IOException) {
                logError("Network Error: ${e.localizedMessage}", e)
                ResponseResource.Failure(isNetworkError = true, errorMessage = "Network error. Please check your connection.")
            } catch (e: HttpException) {
                val errorMessage = parseHttpError(e)
                logError("HTTP ${e.code()} - $errorMessage", e)
                ResponseResource.Failure(isNetworkError = false, errorMessage = errorMessage)
            } catch (e: Exception) {
                logError("Unexpected Error: ${e.localizedMessage}", e)
                ResponseResource.Failure(isNetworkError = true, errorMessage = e.localizedMessage ?: "Unknown error occurred.")
            }
        }
    }

    open fun parseHttpError(exception: HttpException): String {
        return try {
            val errorBody = exception.response()?.errorBody()?.string()
            errorBody?.takeIf { it.isNotBlank() } ?: exception.message()
        } catch (e: Exception) {
            "Server returned an error (HTTP ${exception.code()})"
        }
    }

    open fun logError(message: String, throwable: Throwable? = null) {
        println("BaseRepository Error -> $message")
        throwable?.printStackTrace()
    }
}