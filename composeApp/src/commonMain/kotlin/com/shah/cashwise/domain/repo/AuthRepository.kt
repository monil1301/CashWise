package com.shah.cashwise.domain.repo

import com.shah.cashwise.domain.model.AuthSessionStatus
import kotlinx.coroutines.flow.Flow

/**
 * Account/session boundary. The session is the source of truth for whether sync
 * is available; UI never sees provider SDK types (mapped to [AuthSessionStatus]).
 *
 * Sign-in launches an external browser/PKCE flow, so [signInWithGoogle] returns
 * once the flow is *launched* — success is observed later via [sessionStatus].
 * Email OTP is in-app: [sendEmailOtp] mails a code, [verifyEmailOtp] redeems it
 * and (on success) establishes the session via [sessionStatus].
 *
 * Future seam (not implemented): `suspend fun startPhoneOtp(phone)` / verify.
 */
interface AuthRepository {
    val sessionStatus: Flow<AuthSessionStatus>

    /** Launches the Google OAuth flow. Resolves the session via [sessionStatus]. */
    suspend fun signInWithGoogle(): Result<Unit>

    /** Emails a one-time 6-digit code to [email] (creating the user if needed). */
    suspend fun sendEmailOtp(email: String): Result<Unit>

    /** Redeems the emailed [code] for [email]; success establishes the session. */
    suspend fun verifyEmailOtp(email: String, code: String): Result<Unit>

    suspend fun signOut()
}
