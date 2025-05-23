package com.shah.cashwise.data.api

import com.shah.cashwise.data.model.auth.User
import com.shah.cashwise.data.model.auth.response.AuthUserResponse
import com.shah.cashwise.utils.Constants
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Created by Monil on 02/05/25.
 */

interface AuthApi {
    @POST(Constants.API.PATH.REGISTER_USER)
    suspend fun registerUser(@Body user: User): AuthUserResponse

    @POST(Constants.API.PATH.LOGIN_USER)
    suspend fun loginUser(@Body user: User): AuthUserResponse

}