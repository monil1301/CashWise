package com.shah.cashwise.ui.screens.setpin

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * Navigation outcome of a [SetPinAction]. `null` from [SetPinViewModel.onAction]
 * means the action was handled in place (state changed, no navigation).
 */
enum class SetPinNavResult {
    /** Leave the Set PIN flow (Back pressed on the Create phase). */
    ExitToCaller,

    /** Both PIN entries matched — the PIN is set. The matched PIN is on [SetPinState.createdPin]. */
    Confirmed,
}

/** Holds [SetPinState] and drives the Create → Confirm flow. */
class SetPinViewModel : ViewModel() {

    private val _state = MutableStateFlow(SetPinState())
    val state: StateFlow<SetPinState> = _state.asStateFlow()

    /**
     * Clear all state so a fresh entry into [SetPinScreen] starts on Create
     * with no digits. The Koin-scoped VM otherwise survives across re-entries
     * (e.g. Lock → Set PIN → Back → Set PIN).
     */
    fun reset() {
        _state.update { SetPinState(pinLength = it.pinLength) }
    }

    fun onAction(action: SetPinAction): SetPinNavResult? = when (action) {
        is SetPinAction.DigitPressed -> handleDigit(action.digit)
        SetPinAction.BackspacePressed -> {
            removeDigit()
            null
        }
        SetPinAction.StartOver -> {
            _state.update { SetPinState(pinLength = it.pinLength) }
            null
        }
        SetPinAction.Back -> handleBack()
    }

    private fun handleDigit(digit: Char): SetPinNavResult? {
        val current = _state.value
        if (current.pin.length >= current.pinLength) return null

        val newPin = current.pin + digit
        if (newPin.length < current.pinLength) {
            _state.update { it.copy(pin = newPin, mismatch = false) }
            return null
        }

        // The PIN just filled — branch on phase.
        return when (current.phase) {
            SetPinPhase.Create -> {
                _state.update {
                    SetPinState(
                        phase = SetPinPhase.Confirm,
                        createdPin = newPin,
                        pinLength = it.pinLength,
                    )
                }
                null
            }

            SetPinPhase.Confirm -> {
                if (newPin == current.createdPin) {
                    _state.update { it.copy(pin = newPin, mismatch = false) }
                    SetPinNavResult.Confirmed
                } else {
                    _state.update { it.copy(pin = "", mismatch = true) }
                    null
                }
            }
        }
    }

    private fun removeDigit() {
        _state.update { state ->
            if (state.pin.isEmpty()) state.copy(mismatch = false)
            else state.copy(pin = state.pin.dropLast(1), mismatch = false)
        }
    }

    private fun handleBack(): SetPinNavResult? {
        val current = _state.value
        return when (current.phase) {
            SetPinPhase.Create -> SetPinNavResult.ExitToCaller
            SetPinPhase.Confirm -> {
                _state.update { SetPinState(pinLength = it.pinLength) }
                null
            }
        }
    }
}
