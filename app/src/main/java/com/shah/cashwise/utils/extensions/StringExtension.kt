package com.shah.cashwise.utils.extensions

import android.util.Patterns

/**
 * Created by Monil on 17/05/25.
 */

fun String.isValidEmail(): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

fun String.getInitials(): String {
    if (isNullOrBlank()) return "?"

    val parts = trim()
        .split("\\s+".toRegex()) // Handles multiple spaces
        .filter { it.isNotEmpty() && it[0].isLetter() }

    return when {
        parts.size >= 2 -> "${parts.first()[0]}${parts.last()[0]}"
        parts.size == 1 -> "${parts.first()[0]}"
        else -> "?"
    }.uppercase()
}
