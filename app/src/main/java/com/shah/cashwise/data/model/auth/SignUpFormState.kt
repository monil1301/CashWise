package com.shah.cashwise.data.model.auth

/**
 * Created by Monil on 31/05/25.
 */

data class SignUpFormState(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val nameError: String? = null,
    val emailError: String? = null,
    val passwordError: String? = null,
)