package com.shah.cashwise

import com.shah.cashwise.core.security.PinHasher
import com.shah.cashwise.core.security.secureRandomBytes
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

/** The PIN verifier is hand-rolled PBKDF2, so it gets tested like one. */
class PinHasherTest {

    @Test
    fun correctPinVerifies() {
        val stored = PinHasher.hash("123456")
        assertTrue(PinHasher.verify("123456", stored))
    }

    @Test
    fun wrongPinDoesNotVerify() {
        val stored = PinHasher.hash("123456")
        assertFalse(PinHasher.verify("123457", stored))
        assertFalse(PinHasher.verify("", stored))
        assertFalse(PinHasher.verify("1234567", stored))
    }

    @Test
    fun thePinIsNeverRecoverableFromWhatIsStored() {
        val stored = PinHasher.hash("123456")
        assertFalse(stored.hash.contains("123456"))
        assertFalse(stored.salt.contains("123456"))
    }

    @Test
    fun samePinProducesDifferentHashesSoOneTableCannotCrackEveryInstall() {
        val a = PinHasher.hash("123456")
        val b = PinHasher.hash("123456")
        assertNotEquals(a.salt, b.salt, "each PIN must get a fresh random salt")
        assertNotEquals(a.hash, b.hash)
        // ...and both still verify.
        assertTrue(PinHasher.verify("123456", a))
        assertTrue(PinHasher.verify("123456", b))
    }

    @Test
    fun iterationCountIsStoredSoItCanBeRaisedWithoutInvalidatingExistingPins() {
        val stored = PinHasher.hash("123456")
        assertEquals(PinHasher.ITERATIONS, stored.iterations)
        // A verifier written by an older build (lower cost) must still verify.
        val legacy = stored.copy()
        assertTrue(PinHasher.verify("123456", legacy))
    }

    @Test
    fun secureRandomProducesDistinctNonEmptyBytes() {
        val a = secureRandomBytes(16)
        val b = secureRandomBytes(16)
        assertEquals(16, a.size)
        assertFalse(a.all { it == 0.toByte() }, "must not be all zeroes")
        assertFalse(a.contentEquals(b), "two draws must not be identical")
    }
}
