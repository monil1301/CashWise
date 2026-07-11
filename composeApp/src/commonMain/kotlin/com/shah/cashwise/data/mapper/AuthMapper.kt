package com.shah.cashwise.data.mapper

import com.shah.cashwise.domain.model.AuthSessionStatus
import com.shah.cashwise.domain.model.AuthUser
import io.github.jan.supabase.auth.status.SessionStatus
import io.github.jan.supabase.auth.user.UserInfo
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.jsonPrimitive

/** Maps the Supabase session into the UI-safe [AuthSessionStatus]. */
internal fun SessionStatus.toDomain(): AuthSessionStatus = when (this) {
    is SessionStatus.Authenticated -> AuthSessionStatus.SignedIn(session.user.toAuthUser())
    is SessionStatus.NotAuthenticated -> AuthSessionStatus.SignedOut
    is SessionStatus.RefreshFailure -> AuthSessionStatus.SignedOut
    SessionStatus.Initializing -> AuthSessionStatus.Loading
}

/** OAuth profile fields arrive in [UserInfo.userMetadata] under provider-specific keys. */
internal fun UserInfo?.toAuthUser(): AuthUser {
    if (this == null) {
        return AuthUser(id = "", email = null, displayName = null, avatarUrl = null)
    }
    fun meta(key: String): String? = userMetadata?.get(key)?.jsonPrimitive?.contentOrNull
    return AuthUser(
        id = id,
        email = email,
        displayName = meta("full_name") ?: meta("name"),
        avatarUrl = meta("avatar_url") ?: meta("picture"),
    )
}
