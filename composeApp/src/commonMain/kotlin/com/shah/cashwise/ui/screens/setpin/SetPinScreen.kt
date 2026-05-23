package com.shah.cashwise.ui.screens.setpin

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shah.cashwise.di.appModules
import com.shah.cashwise.ui.screens.setpin.layout.SetPinCompactLayout
import com.shah.cashwise.ui.screens.setpin.layout.SetPinExpandedLayout
import com.shah.cashwise.ui.screens.setpin.layout.SetPinMediumLayout
import com.shah.cashwise.ui.theme.CashWiseTheme
import org.koin.compose.KoinApplication
import org.koin.compose.viewmodel.koinViewModel

private enum class SetPinLayoutType {
    Compact,
    Medium,
    Expanded,
}

/**
 * Two-phase Set PIN flow (Create → Confirm). The user enters a [SetPinState.pinLength]-digit
 * PIN on the keypad; once it fills, the screen advances to the Confirm phase.
 * Matching the same PIN there reports the result through [onConfirmed]; pressing
 * Back on the Create phase invokes [onExit].
 */
@Composable
fun SetPinScreen(
    onExit: () -> Unit,
    onConfirmed: (pin: String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val viewModel = koinViewModel<SetPinViewModel>()
    val state by viewModel.state.collectAsState()
    val onExitState by rememberUpdatedState(onExit)
    val onConfirmedState by rememberUpdatedState(onConfirmed)

    // Koin scopes the VM to the host ViewModelStoreOwner, so it survives
    // across Lock ↔ Set PIN round-trips — clear it on each fresh entry.
    LaunchedEffect(Unit) {
        viewModel.reset()
    }

    val handleAction: (SetPinAction) -> Unit = { action ->
        when (viewModel.onAction(action)) {
            SetPinNavResult.ExitToCaller -> onExitState()
            SetPinNavResult.Confirmed -> onConfirmedState(viewModel.state.value.createdPin)
            null -> Unit
        }
    }

    BoxWithConstraints(modifier = modifier.fillMaxSize()) {
        val layoutType = when {
            maxWidth < 600.dp -> SetPinLayoutType.Compact
            maxWidth < 840.dp -> SetPinLayoutType.Medium
            else -> SetPinLayoutType.Expanded
        }

        when (layoutType) {
            SetPinLayoutType.Compact -> SetPinCompactLayout(
                state = state,
                onAction = handleAction,
                modifier = Modifier.fillMaxSize(),
            )

            SetPinLayoutType.Medium -> SetPinMediumLayout(
                state = state,
                onAction = handleAction,
                modifier = Modifier.fillMaxSize(),
            )

            SetPinLayoutType.Expanded -> SetPinExpandedLayout(
                state = state,
                onAction = handleAction,
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}

@Preview(name = "Set PIN Compact", widthDp = 390, heightDp = 844)
@Composable
private fun SetPinScreenCompactPreview() {
    KoinApplication(application = { modules(appModules) }) {
        CashWiseTheme {
            SetPinScreen(onExit = {}, onConfirmed = {})
        }
    }
}

@Preview(name = "Set PIN Medium", widthDp = 800, heightDp = 1000)
@Composable
private fun SetPinScreenMediumPreview() {
    KoinApplication(application = { modules(appModules) }) {
        CashWiseTheme {
            SetPinScreen(onExit = {}, onConfirmed = {})
        }
    }
}

@Preview(name = "Set PIN Expanded", widthDp = 1200, heightDp = 900)
@Composable
private fun SetPinScreenExpandedPreview() {
    KoinApplication(application = { modules(appModules) }) {
        CashWiseTheme {
            SetPinScreen(onExit = {}, onConfirmed = {})
        }
    }
}
