package com.shah.cashwise.core.security

import okio.ByteString
import okio.ByteString.Companion.decodeHex
import okio.ByteString.Companion.toByteString

/**
 * Derives a verifier for the app-lock PIN. Never store the PIN itself.
 *
 * A 4–6 digit PIN has at most a million combinations, so a *fast* hash (a bare SHA-256) is
 * worthless — an attacker who reads the stored value cracks it in under a second. The defence
 * is to make each guess expensive: PBKDF2-HMAC-SHA256 with a high iteration count turns an
 * exhaustive search from seconds into days, and a random per-PIN salt stops one precomputed
 * table from cracking every install.
 *
 * That is a cost multiplier, not a guarantee. It is the *second* line of defence — the first
 * is [com.shah.cashwise.data.local.SecureStore], which keeps the verifier out of an
 * attacker's hands to begin with (Keychain on iOS, Keystore-backed on Android).
 *
 * PBKDF2 is hand-rolled because Kotlin Multiplatform has no common KDF; okio's `hmacSha256`
 * is the one cryptographic primitive available in common code.
 */
object PinHasher {

    /** Tuned so a single derivation costs a noticeable fraction of a second on a phone. */
    const val ITERATIONS: Int = 120_000

    private const val SALT_BYTES = 16
    private const val KEY_BYTES = 32 // one SHA-256 block: no need to loop over blocks

    /**
     * Generates a fresh salt and derives the verifier for [pin].
     *
     * @throws IllegalArgumentException if [pin] is empty — an empty PIN is not a lock, and
     *  HMAC rejects an empty key anyway.
     */
    fun hash(pin: String): PinHash {
        require(pin.isNotEmpty()) { "PIN must not be empty" }
        val salt = secureRandomBytes(SALT_BYTES)
        return PinHash(
            salt = salt.toByteString().hex(),
            iterations = ITERATIONS,
            hash = derive(pin, salt, ITERATIONS).hex(),
        )
    }

    /**
     * True when [pin] reproduces [stored]. Re-derives with the stored salt and iterations.
     *
     * An empty [pin] is simply wrong, never an error: an unlock screen starts empty and would
     * otherwise crash on the first keystroke (HMAC will not take an empty key).
     */
    fun verify(pin: String, stored: PinHash): Boolean {
        if (pin.isEmpty()) return false
        val salt = stored.salt.decodeHex().toByteArray()
        val candidate = derive(pin, salt, stored.iterations).hex()
        return constantTimeEquals(candidate, stored.hash)
    }

    /**
     * PBKDF2-HMAC-SHA256, one output block (dkLen == hLen == 32), i.e.
     * `T1 = U1 xor U2 xor ... xor Uc`, `U1 = PRF(password, salt || INT(1))`.
     */
    private fun derive(pin: String, salt: ByteArray, iterations: Int): ByteString {
        val password = pin.encodeToByteArray().toByteString()

        // U1 = HMAC(password, salt || big-endian 1)
        val block = salt + byteArrayOf(0, 0, 0, 1)
        var u = block.toByteString().hmacSha256(password)
        val result = u.toByteArray()

        repeat(iterations - 1) {
            u = u.hmacSha256(password)
            val current = u.toByteArray()
            for (i in result.indices) {
                result[i] = (result[i].toInt() xor current[i].toInt()).toByte()
            }
        }
        return result.toByteString()
    }

    /** Compares without an early exit, so timing can't leak how much of the hash matched. */
    private fun constantTimeEquals(a: String, b: String): Boolean {
        if (a.length != b.length) return false
        var difference = 0
        for (i in a.indices) {
            difference = difference or (a[i].code xor b[i].code)
        }
        return difference == 0
    }
}

/**
 * The stored PIN verifier. [iterations] is persisted alongside the hash so the cost can be
 * raised later without invalidating PINs already set on older builds.
 */
data class PinHash(
    val salt: String,
    val iterations: Int,
    val hash: String,
)
