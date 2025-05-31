package com.shah.cashwise.data.model.auth

/**
 * Created by Monil on 31/05/25.
 */

data class LoginFormState(
    val email: String = "",
    val password: String = "",
    val emailError: String? = null,
    val passwordError: String? = null,
)
