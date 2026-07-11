package com.shah.cashwise.ui.screens.signin

/** UI events emitted by [SignInScreen], handled by [SignInViewModel.onAction]. */
sealed interface SignInAction {
    /** Top-bar back button: step back through the flow, or exit from Providers. */
    data object Back : SignInAction

    /** "Continue with Google" pressed. */
    data object ContinueWithGoogle : SignInAction

    /** "Continue with Email" pressed — opens the email-entry step. */
    data object ContinueWithEmail : SignInAction

    /** "Use Phone (OTP) instead" pressed (not implemented yet). */
    data object UsePhoneOtp : SignInAction

    /** Dismiss the current error/notice message. */
    data object DismissError : SignInAction

    /** Email field edited. */
    data class EmailChanged(val value: String) : SignInAction

    /** "Send code" pressed on the email step. */
    data object SendEmailCode : SignInAction

    /** Code field edited (digits only, capped at the code length). */
    data class CodeChanged(val value: String) : SignInAction

    /** "Verify" pressed on the code step. */
    data object VerifyCode : SignInAction

    /** "Resend code" pressed on the code step. */
    data object ResendCode : SignInAction
}
