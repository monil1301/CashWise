package com.shah.cashwise.data.repo

import com.shah.cashwise.domain.model.AuthSessionStatus
import com.shah.cashwise.domain.repo.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

/**
 * Used when no Supabase credentials are configured (blank `SupabaseConfig`).
 * Keeps the app fully usable offline: there is never a session, and a sign-in
 * attempt fails fast so the UI can surface a "sync not configured" message.
 */
class OfflineAuthRepository : AuthRepository {

    override val sessionStatus: Flow<AuthSessionStatus> = flowOf(AuthSessionStatus.SignedOut)

    override suspend fun signInWithGoogle(): Result<Unit> = notConfigured()

    override suspend fun sendEmailOtp(email: String): Result<Unit> = notConfigured()

    override suspend fun verifyEmailOtp(email: String, code: String): Result<Unit> = notConfigured()

    override suspend fun signOut() = Unit

    private fun notConfigured(): Result<Unit> =
        Result.failure(IllegalStateException("Sync is not configured"))
}
