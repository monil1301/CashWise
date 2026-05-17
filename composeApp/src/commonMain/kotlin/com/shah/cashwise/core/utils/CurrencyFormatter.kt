package com.shah.cashwise.core.utils

const val RUPEE_SYMBOL = "\u20B9"

fun formatIndianCurrency(
    amount: Long,
    symbol: String = RUPEE_SYMBOL,
): String = symbol + formatIndianNumber(amount)

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
