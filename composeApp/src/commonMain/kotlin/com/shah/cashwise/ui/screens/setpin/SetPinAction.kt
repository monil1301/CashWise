package com.shah.cashwise.ui.screens.setpin

/** UI events emitted by [SetPinScreen], handled by [SetPinViewModel.onAction]. */
sealed interface SetPinAction {
    /** Top-bar back button: drop back to Create from Confirm, or exit on Create. */
    data object Back : SetPinAction

    /** A keypad digit was pressed. */
    data class DigitPressed(val digit: Char) : SetPinAction

    /** The keypad backspace key was pressed. */
    data object BackspacePressed : SetPinAction

    /** "Start over" — clear everything and return to the Create phase. */
    data object StartOver : SetPinAction
}
