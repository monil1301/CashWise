package com.shah.cashwise.ui.screens.signin

import org.jetbrains.compose.resources.StringResource

/** Sign-in providers offered on the screen. */
enum class AuthProvider {
    Google,
    Email,
    Phone,
}

/** Which step of the sign-in flow is on screen. */
enum class SignInStep {
    /** The provider list (Google / Email / Phone). */
    Providers,

    /** Email entry — collecting the address to send a code to. */
    EmailEntry,

    /** Code entry — verifying the 6-digit code emailed to [SignInState.email]. */
    CodeEntry,
}

/**
 * State for [SignInScreen]. [loadingProvider] marks an in-flight social provider;
 * [isSubmitting] covers the email send/verify calls. [errorMessage] surfaces a
 * failure or a "coming soon" notice as a string resource.
 */
data class SignInState(
    val step: SignInStep = SignInStep.Providers,
    val loadingProvider: AuthProvider? = null,
    val email: String = "",
    val code: String = "",
    val isSubmitting: Boolean = false,
    val errorMessage: StringResource? = null,
) {
    val isBusy: Boolean get() = loadingProvider != null || isSubmitting

    /** Lightweight client-side email check to gate the "Send code" button. */
    val isEmailValid: Boolean
        get() = email.trim().let { it.length >= 5 && it.contains('@') && it.substringAfterLast('@').contains('.') }

    val isCodeValid: Boolean get() = code.length == CODE_LENGTH

    companion object {
        const val CODE_LENGTH: Int = 6
    }
}
