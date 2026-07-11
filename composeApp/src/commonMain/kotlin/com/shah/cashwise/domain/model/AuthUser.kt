package com.shah.cashwise.domain.model

/**
 * The signed-in account, mapped from the auth provider into a UI-safe domain
 * model so provider types never leak past the data layer.
 */
data class AuthUser(
    val id: String,
    val email: String?,
    val displayName: String?,
    val avatarUrl: String?,
)
