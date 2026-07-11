package com.shah.cashwise

import com.shah.cashwise.core.extensions.toMinorUnits
import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * Guards the money parser. The previous implementation went through `toDoubleOrNull()`,
 * which silently returned 0 for grouped or comma-decimal input — a starting balance or
 * budget the user typed would just vanish.
 */
class MoneyExtensionsTest {

    @Test
    fun parsesPlainAmounts() {
        assertEquals(0L, "".toMinorUnits())
        assertEquals(0L, "   ".toMinorUnits())
        assertEquals(50000L, "500".toMinorUnits())
        assertEquals(50025L, "500.25".toMinorUnits())
        assertEquals(50050L, "500.5".toMinorUnits(), "one decimal digit pads to two")
    }

    @Test
    fun parsesGroupedAmounts() {
        // Indian grouping — the app is INR-first, so this must not silently become 0.
        assertEquals(12000000L, "1,20,000".toMinorUnits())
        assertEquals(120050L, "1,200.50".toMinorUnits())
        assertEquals(100000000L, "1,000,000".toMinorUnits())
    }

    @Test
    fun parsesCommaAsDecimalSeparator() {
        assertEquals(120050L, "1200,50".toMinorUnits())
        assertEquals(50025L, "500,25".toMinorUnits())
    }

    @Test
    fun truncatesBeyondTwoFractionDigits() {
        // 3+ digits after the separator means it wasn't a decimal point (grouping).
        assertEquals(1234567800L, "12,345,678".toMinorUnits())
    }

    @Test
    fun unparseableInputIsZeroRatherThanACrash() {
        assertEquals(0L, "abc".toMinorUnits())
        assertEquals(0L, ".".toMinorUnits())
        assertEquals(0L, "-".toMinorUnits())
        // "NaN" used to reach Double.roundToLong(), which throws on Kotlin/Native.
        assertEquals(0L, "NaN".toMinorUnits())
        assertEquals(0L, "Infinity".toMinorUnits())
    }

    @Test
    fun overflowIsClampedToZeroRatherThanWrappingNegative() {
        assertEquals(0L, "99999999999999999999".toMinorUnits())
    }
}
