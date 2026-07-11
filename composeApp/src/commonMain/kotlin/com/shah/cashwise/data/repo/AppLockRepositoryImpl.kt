package com.shah.cashwise.data.repo

import com.shah.cashwise.core.security.PinHash
import com.shah.cashwise.core.security.PinHasher
import com.shah.cashwise.data.local.SecureStore
import com.shah.cashwise.domain.repo.AppLockRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext

/**
 * [AppLockRepository] backed by a [SecureStore]. The verifier is serialised as
 * `iterations:salt:hash` — the iteration count travels with it so the cost can be raised in a
 * later release without invalidating PINs already set.
 *
 * Hashing runs on [dispatcher]: PBKDF2 is intentionally slow (~100ms), which is exactly what
 * must never happen on the main thread.
 */
class AppLockRepositoryImpl(
    private val secureStore: SecureStore,
    private val dispatcher: CoroutineDispatcher,
) : AppLockRepository {

    private val lockEnabled = MutableStateFlow(false)

    override val isLockEnabled: Flow<Boolean> = lockEnabled.asStateFlow()

    /** Called once at startup so [isLockEnabled] reflects storage before the shell gates on it. */
    suspend fun refresh() {
        lockEnabled.value = withContext(dispatcher) { secureStore.get(PIN_KEY) != null }
    }

    override suspend fun setPin(pin: String): Result<Unit> = withContext(dispatcher) {
        runCatching {
            val hash = PinHasher.hash(pin)
            secureStore.put(PIN_KEY, "${hash.iterations}:${hash.salt}:${hash.hash}")
            lockEnabled.value = true
        }
    }

    override suspend fun verifyPin(pin: String): Boolean = withContext(dispatcher) {
        val stored = secureStore.get(PIN_KEY)?.let(::parse) ?: return@withContext false
        PinHasher.verify(pin, stored)
    }

    override suspend fun clearPin() {
        withContext(dispatcher) { secureStore.remove(PIN_KEY) }
        lockEnabled.value = false
    }

    private fun parse(raw: String): PinHash? {
        val parts = raw.split(':')
        if (parts.size != 3) return null
        val iterations = parts[0].toIntOrNull() ?: return null
        return PinHash(salt = parts[1], iterations = iterations, hash = parts[2])
    }

    private companion object {
        const val PIN_KEY = "app_lock_pin"
    }
}
