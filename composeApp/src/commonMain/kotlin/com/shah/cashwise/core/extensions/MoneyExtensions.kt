package com.shah.cashwise.core.extensions

/** Minor units in one major unit (paise per rupee, cents per dollar). */
const val MINOR_UNITS_PER_MAJOR: Long = 100L

/** Digits after the decimal point. Zero-decimal currencies (JPY) are not modelled yet. */
private const val FRACTION_DIGITS: Int = 2

/**
 * Parses user-typed money into minor units: `"1200.50"` and `"1,200.50"` and `"1200,50"`
 * all become `120050`.
 *
 * Parsed from the string rather than through a [Double]. Going via `toDoubleOrNull()`
 * silently returned 0 for anything it didn't recognise — grouped input like `"1,20,000"`
 * (ordinary in an INR-first app) or a comma decimal separator — which meant a starting
 * balance or budget quietly became zero. `Double` also accepts `"NaN"`, whose
 * `roundToLong()` throws on Kotlin/Native but returns 0 on JVM.
 *
 * A trailing separator followed by 1–2 digits is treated as the decimal point; any other
 * `.`/`,` is a grouping separator and is dropped. Unparseable input and overflow yield 0.
 */
fun String.toMinorUnits(): Long {
    val input = trim()
    if (input.isEmpty()) return 0L

    val separator = input.indexOfLast { it == '.' || it == ',' }
    val fractionLength = if (separator >= 0) input.length - separator - 1 else 0
    val hasDecimalPoint = separator >= 0 && fractionLength in 1..FRACTION_DIGITS

    val majorText = if (hasDecimalPoint) input.take(separator) else input
    val fractionText = if (hasDecimalPoint) input.substring(separator + 1) else ""

    val majorDigits = majorText.filter(Char::isDigit)
    val fractionDigits = fractionText.filter(Char::isDigit)
    if (majorDigits.isEmpty() && fractionDigits.isEmpty()) return 0L

    val major = majorDigits.toLongOrNull() ?: return 0L
    val minor = fractionDigits.padEnd(FRACTION_DIGITS, '0').take(FRACTION_DIGITS).toLongOrNull() ?: 0L

    val total = major * MINOR_UNITS_PER_MAJOR + minor
    // Long overflow wraps negative; a negative amount is never valid input here.
    return if (total < 0L) 0L else total
}
