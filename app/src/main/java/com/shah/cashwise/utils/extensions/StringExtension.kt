package com.shah.cashwise.utils.extensions

import android.util.Patterns

/**
 * Created by Monil on 17/05/25.
 */

fun String.isValidEmail(): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(this).matches()
}
