package com.shah.cashwise

import com.shah.cashwise.ui.screens.setpin.SetPinAction
import com.shah.cashwise.ui.screens.setpin.SetPinNavResult
import com.shah.cashwise.ui.screens.setpin.SetPinPhase
import com.shah.cashwise.ui.screens.setpin.SetPinViewModel
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

class SetPinViewModelTest {

    @Test
    fun digitsAppendUntilFilled() {
        val viewModel = SetPinViewModel()

        "12345".forEach { viewModel.onAction(SetPinAction.DigitPressed(it)) }

        val state = viewModel.state.value
        assertEquals("12345", state.pin)
        assertEquals(SetPinPhase.Create, state.phase)
        assertFalse(state.isFilled)
    }

    @Test
    fun fillingPinAdvancesToConfirmPhase() {
        val viewModel = SetPinViewModel()

        "123456".forEach { viewModel.onAction(SetPinAction.DigitPressed(it)) }

        val state = viewModel.state.value
        assertEquals(SetPinPhase.Confirm, state.phase)
        assertEquals("123456", state.createdPin)
        assertEquals("", state.pin)
    }

    @Test
    fun mismatchedConfirmClearsPinAndFlagsMismatch() {
        val viewModel = SetPinViewModel()
        "123456".forEach { viewModel.onAction(SetPinAction.DigitPressed(it)) }

        val result = "654321".map {
            viewModel.onAction(SetPinAction.DigitPressed(it))
        }.last()

        assertNull(result)
        val state = viewModel.state.value
        assertEquals(SetPinPhase.Confirm, state.phase)
        assertEquals("", state.pin)
        assertTrue(state.mismatch)
    }

    @Test
    fun matchedConfirmReturnsConfirmedResult() {
        val viewModel = SetPinViewModel()
        "123456".forEach { viewModel.onAction(SetPinAction.DigitPressed(it)) }

        val result = "123456".map {
            viewModel.onAction(SetPinAction.DigitPressed(it))
        }.last()

        assertEquals(SetPinNavResult.Confirmed, result)
        assertEquals("123456", viewModel.state.value.createdPin)
    }

    @Test
    fun backspaceRemovesLastDigitAndClearsMismatch() {
        val viewModel = SetPinViewModel()
        "12".forEach { viewModel.onAction(SetPinAction.DigitPressed(it)) }

        viewModel.onAction(SetPinAction.BackspacePressed)
        assertEquals("1", viewModel.state.value.pin)

        viewModel.onAction(SetPinAction.BackspacePressed)
        viewModel.onAction(SetPinAction.BackspacePressed) // no-op when empty
        assertEquals("", viewModel.state.value.pin)
        assertFalse(viewModel.state.value.mismatch)
    }

    @Test
    fun startOverResetsToCreatePhase() {
        val viewModel = SetPinViewModel()
        "123456".forEach { viewModel.onAction(SetPinAction.DigitPressed(it)) }
        assertEquals(SetPinPhase.Confirm, viewModel.state.value.phase)

        viewModel.onAction(SetPinAction.StartOver)

        val state = viewModel.state.value
        assertEquals(SetPinPhase.Create, state.phase)
        assertEquals("", state.createdPin)
        assertEquals("", state.pin)
        assertFalse(state.mismatch)
    }

    @Test
    fun backOnCreateExitsToCaller() {
        val viewModel = SetPinViewModel()
        assertEquals(SetPinNavResult.ExitToCaller, viewModel.onAction(SetPinAction.Back))
    }

    @Test
    fun backOnConfirmReturnsToCreatePhase() {
        val viewModel = SetPinViewModel()
        "123456".forEach { viewModel.onAction(SetPinAction.DigitPressed(it)) }
        assertEquals(SetPinPhase.Confirm, viewModel.state.value.phase)

        assertNull(viewModel.onAction(SetPinAction.Back))
        assertEquals(SetPinPhase.Create, viewModel.state.value.phase)
        assertEquals("", viewModel.state.value.createdPin)
    }
}
