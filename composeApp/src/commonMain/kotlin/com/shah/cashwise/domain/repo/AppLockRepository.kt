package com.shah.cashwise.domain.repo

import kotlinx.coroutines.flow.Flow

/**
 * The app-lock PIN. The PIN itself is never stored — only a slow, salted verifier derived
 * from it, kept in the platform's protected storage.
 *
 * [isLockEnabled] is a [Flow] because the app shell gates on it at launch; setting and
 * verifying are one-shot suspends because they are deliberately expensive (see `PinHasher`).
 */
interface AppLockRepository {

    /** Emits whether a PIN is currently set — i.e. whether the app should lock on launch. */
    val isLockEnabled: Flow<Boolean>

    /** Stores a verifier for [pin], replacing any existing one. */
    suspend fun setPin(pin: String): Result<Unit>

    /** True when [pin] matches the stored verifier. False when no PIN is set. */
    suspend fun verifyPin(pin: String): Boolean

    /** Removes the PIN and disables the lock. */
    suspend fun clearPin()
}
