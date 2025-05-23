package com.shah.cashwise.utils

/**
 * Created by Monil on 17/05/25.
 */

object Constants {
    object API {
        const val BASE_URL = "https://b5ab-2401-4900-8838-e4bd-d119-7922-1b54-fdc8.ngrok-free.app/"

        object PATH {
            // Auth
            private const val AUTH = "auth"
            const val REGISTER_USER = "$AUTH/registerUser"
            const val LOGIN_USER = "$AUTH/loginUser"
        }

        object HEADERS {
            const val ACCEPT = "Accept"
            const val APPLICATION_JSON = "application/json"
            const val CONTENT_TYPE = "Content-Type"
            const val AUTHORIZATION = "Authorization"
            private const val BEARER = "Bearer"

            fun getBearerToken(token: String?): String = "$BEARER $token"
        }
    }
}