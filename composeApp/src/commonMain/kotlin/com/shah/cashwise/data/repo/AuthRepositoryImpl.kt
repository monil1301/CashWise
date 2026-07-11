package com.shah.cashwise.data.repo

import com.shah.cashwise.data.mapper.toDomain
import com.shah.cashwise.domain.model.AuthSessionStatus
import com.shah.cashwise.domain.repo.AuthRepository
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.OtpType
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.Google
import io.github.jan.supabase.auth.providers.builtin.OTP
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Supabase-backed [AuthRepository]. The Supabase client persists and refreshes
 * the session itself; we only expose its [sessionStatus] as a domain flow.
 * Registered only when credentials are configured (else [OfflineAuthRepository]).
 */
class AuthRepositoryImpl(
    private val supabase: SupabaseClient,
) : AuthRepository {

    override val sessionStatus: Flow<AuthSessionStatus> =
        supabase.auth.sessionStatus.map { it.toDomain() }

    override suspend fun signInWithGoogle(): Result<Unit> = runCatching {
        // Launches the external browser flow; the resulting session arrives via the
        // com.shah.cashwise://auth-callback deep link and surfaces on [sessionStatus].
        supabase.auth.signInWith(Google)
    }

    override suspend fun sendEmailOtp(email: String): Result<Unit> = runCatching {
        // createUser = true means a new address is mailed the "Confirm signup"
        // template and an existing one the "Magic Link" template. BOTH must render
        // {{ .Token }} (not {{ .ConfirmationURL }}) or the user gets a link, not a
        // code — and the OTP length must be 6 to match SignInState.CODE_LENGTH.
        supabase.auth.signInWith(OTP) {
            this.email = email
            createUser = true
        }
    }

    override suspend fun verifyEmailOtp(email: String, code: String): Result<Unit> = runCatching {
        // A returning user's code is a magic-link (EMAIL) OTP, but a brand-new
        // address created via createUser=true is confirmed with a SIGNUP OTP. We
        // can't tell which up front, so try EMAIL and fall back to SIGNUP. A failed
        // verify doesn't consume the token, so the retry is safe.
        supabase.auth.verifyEmailOtp(type = OtpType.Email.EMAIL, email = email, token = code)
        Unit
    }.recoverCatching { error ->
        if (error is CancellationException) throw error
        supabase.auth.verifyEmailOtp(type = OtpType.Email.SIGNUP, email = email, token = code)
        Unit
    }

    override suspend fun signOut() {
        supabase.auth.signOut()
    }
}
