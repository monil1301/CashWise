package com.shah.cashwise

import com.shah.cashwise.core.utils.formatIndianCurrency
import com.shah.cashwise.core.utils.formatIndianNumber
import com.shah.cashwise.core.utils.formatMinorUnits
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ComposeAppCommonTest {

    @Test
    fun formatsIndianNumberGroups() {
        assertEquals("1,00,000", formatIndianNumber(100000))
        assertEquals("12,34,567", formatIndianNumber(1234567))
    }

    @Test
    fun formatsIndianCurrencyWithRupeeSymbol() {
        assertEquals("₹98,765", formatIndianCurrency(98765))
    }

    @Test
    fun preservesNegativeValues() {
        assertEquals("-4,20,000", formatIndianNumber(-420000))
    }

    @Test
    fun formatsMinorUnitsAsWholeUnitsWhenThereAreNoPaise() {
        assertEquals("₹4,200", formatMinorUnits(420_000))
        assertEquals("₹1,24,300", formatMinorUnits(12_430_000))
    }

    /** The sign leads the symbol: an overdrawn card reads "-₹8,900", never "₹-8,900". */
    @Test
    fun putsTheMinusSignBeforeTheCurrencySymbol() {
        assertEquals("-₹8,900", formatMinorUnits(-890_000))
    }

    @Test
    fun showsPaiseOnlyWhenNonZero() {
        assertEquals("₹1,200.50", formatMinorUnits(120_050))
        assertEquals("₹1,200.05", formatMinorUnits(120_005))
        assertEquals("₹1,200", formatMinorUnits(120_000))
    }

    @Test
    fun formatsMinorUnitsWithANonRupeeSymbol() {
        assertEquals("$12.34", formatMinorUnits(1234, symbol = "$"))
    }

    /** Long.MIN_VALUE has no positive counterpart; negating it must not overflow. */
    @Test
    fun survivesLongMinValue() {
        assertTrue(formatMinorUnits(Long.MIN_VALUE).startsWith("-₹"))
    }
}
