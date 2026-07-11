package com.shah.cashwise.core.utils

import kotlin.math.abs

const val RUPEE_SYMBOL = "\u20B9"

fun formatIndianCurrency(
    amount: Long,
    symbol: String = RUPEE_SYMBOL,
): String = symbol + formatIndianNumber(amount)

/**
 * Formats a **minor-unit** amount (paise) for display, e.g. `-890000` → `-₹8,900`.
 *
 * The sign leads the symbol rather than following it: `-₹8,900`, never `₹-8,900`. Paise are
 * shown only when non-zero, so a whole-rupee balance stays clean but a 50-paise one is not
 * silently rounded away.
 */
fun formatMinorUnits(
    minorUnits: Long,
    symbol: String = RUPEE_SYMBOL,
): String {
    val negative = minorUnits < 0
    // Guard Long.MIN_VALUE, whose magnitude has no positive Long counterpart.
    val magnitude = if (minorUnits == Long.MIN_VALUE) Long.MAX_VALUE else abs(minorUnits)
    val major = magnitude / 100
    val minor = magnitude % 100

    return buildString {
        if (negative) append('-')
        append(symbol)
        append(formatIndianNumber(major))
        if (minor != 0L) {
            append('.')
            append(minor.toString().padStart(2, '0'))
        }
    }
}

fun formatIndianNumber(value: Long): String {
    val rawValue = value.toString()
    val isNegative = rawValue.startsWith('-')
    val digits = rawValue.removePrefix("-")

    if (digits.length <= 3) {
        return if (isNegative) "-$digits" else digits
    }

    val lastGroup = digits.takeLast(3)
    var leadingDigits = digits.dropLast(3)
    val groups = mutableListOf<String>()

    while (leadingDigits.length > 2) {
        groups.add(leadingDigits.takeLast(2))
        leadingDigits = leadingDigits.dropLast(2)
    }

    if (leadingDigits.isNotEmpty()) {
        groups.add(leadingDigits)
    }

    val formatted = buildString {
        append(groups.asReversed().joinToString(","))
        append(",")
        append(lastGroup)
    }

    return if (isNegative) "-$formatted" else formatted
}
