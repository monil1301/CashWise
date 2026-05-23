package com.shah.cashwise.ui.screens.setpin

/** Which half of the two-step Set PIN flow the user is in. */
enum class SetPinPhase {
    /** First entry — the user is creating a new PIN. */
    Create,

    /** Second entry — the user is re-entering the PIN to confirm it. */
    Confirm,
}

/**
 * State for [SetPinScreen]. The user types digits into [pin] until it reaches
 * [pinLength]; once it does, the [phase] handler decides whether to advance to
 * Confirm or, if the two PINs match, emit a confirmation result.
 *
 * [mismatch] flips on briefly when the Confirm PIN does not match Create —
 * cleared on the next digit press or [SetPinAction.StartOver].
 */
data class SetPinState(
    val phase: SetPinPhase = SetPinPhase.Create,
    val createdPin: String = "",
    val pin: String = "",
    val pinLength: Int = DEFAULT_PIN_LENGTH,
    val mismatch: Boolean = false,
) {
    /** True when [pin] has reached [pinLength] and is ready to be processed. */
    val isFilled: Boolean
        get() = pin.length == pinLength

    companion object {
        /** The default PIN length used across Cashwise. */
        const val DEFAULT_PIN_LENGTH: Int = 6
    }
}
