package com.shah.cashwise

import com.shah.cashwise.core.utils.formatIndianCurrency
import com.shah.cashwise.core.utils.formatIndianNumber
import kotlin.test.Test
import kotlin.test.assertEquals

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
}
