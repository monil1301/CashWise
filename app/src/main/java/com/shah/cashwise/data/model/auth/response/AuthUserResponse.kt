package com.shah.cashwise.data.model.auth.response

import com.squareup.moshi.Json

/**
 * Created by Monil on 18/05/25.
 */

data class AuthUserResponse(
    @Json(name = "_id") val id: String,
    val email: String,
    val firebaseUId: String,
    val name: String
)