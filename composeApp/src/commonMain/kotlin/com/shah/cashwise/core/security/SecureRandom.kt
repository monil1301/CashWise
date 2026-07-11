package com.shah.cashwise.core.security

/**
 * Cryptographically secure random bytes. `kotlin.random.Random` is explicitly NOT suitable —
 * it is a predictable PRNG, and a guessable salt defeats the point of salting.
 */
expect fun secureRandomBytes(size: Int): ByteArray
