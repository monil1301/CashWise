package com.shah.cashwise.ui.screens.setup

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shah.cashwise.di.appModules
import com.shah.cashwise.ui.screens.setpin.SetPinScreen
import com.shah.cashwise.ui.screens.setup.layout.SetupCompactLayout
import com.shah.cashwise.ui.screens.setup.layout.SetupExpandedLayout
import com.shah.cashwise.ui.screens.setup.layout.SetupMediumLayout
import com.shah.cashwise.ui.theme.CashWiseTheme
import org.koin.compose.KoinApplication
import org.koin.compose.viewmodel.koinViewModel

private enum class SetupLayoutType {
    Compact,
    Medium,
    Expanded,
}

/**
 * Multi-step first-run setup flow. Step count follows the selected
 * [WalletType] — Solo is four steps, Shared adds an "Invite members" step at
 * the end (see [SetupState.totalSteps]).
 *
 * The Lock step can hand off to [SetPinScreen] as a sub-route (the PIN screen
 * has its own chrome, so it is not a counted step). Confirming the PIN advances
 * the underlying flow by one step; backing out returns to the Lock step.
 *
 * @param onExit invoked when the user backs out of the first step.
 * @param onFinished invoked when the final step's Continue is pressed.
 */
@Composable
fun SetupScreen(
    onExit: () -> Unit,
    onFinished: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val viewModel = koinViewModel<SetupViewModel>()
    val state by viewModel.state.collectAsState()
    val onExitState by rememberUpdatedState(onExit)
    val onFinishedState by rememberUpdatedState(onFinished)

    val handleAction: (SetupAction) -> Unit = { action ->
        when (viewModel.onAction(action)) {
            SetupNavResult.ExitToWelcome -> onExitState()
            SetupNavResult.Finished -> onFinishedState()
            null -> Unit
        }
    }

    if (state.showSetPin) {
        SetPinScreen(
            onExit = { handleAction(SetupAction.SetPinDismissed) },
            onConfirmed = { pin -> handleAction(SetupAction.SetPinConfirmed(pin)) },
            modifier = modifier.fillMaxSize(),
        )
        return
    }

    BoxWithConstraints(modifier = modifier.fillMaxSize()) {
        val layoutType = when {
            maxWidth < 600.dp -> SetupLayoutType.Compact
            maxWidth < 840.dp -> SetupLayoutType.Medium
            else -> SetupLayoutType.Expanded
        }

        when (layoutType) {
            SetupLayoutType.Compact -> SetupCompactLayout(
                state = state,
                onAction = handleAction,
                modifier = Modifier.fillMaxSize(),
            )

            SetupLayoutType.Medium -> SetupMediumLayout(
                state = state,
                onAction = handleAction,
                modifier = Modifier.fillMaxSize(),
            )

            SetupLayoutType.Expanded -> SetupExpandedLayout(
                state = state,
                onAction = handleAction,
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}

@Preview(name = "Setup Compact", widthDp = 390, heightDp = 844)
@Composable
private fun SetupScreenCompactPreview() {
    KoinApplication(application = { modules(appModules) }) {
        CashWiseTheme {
            SetupScreen(onExit = {}, onFinished = {})
        }
    }
}

@Preview(name = "Setup Medium", widthDp = 800, heightDp = 1000)
@Composable
private fun SetupScreenMediumPreview() {
    KoinApplication(application = { modules(appModules) }) {
        CashWiseTheme {
            SetupScreen(onExit = {}, onFinished = {})
        }
    }
}

@Preview(name = "Setup Expanded", widthDp = 1200, heightDp = 900)
@Composable
private fun SetupScreenExpandedPreview() {
    KoinApplication(application = { modules(appModules) }) {
        CashWiseTheme {
            SetupScreen(onExit = {}, onFinished = {})
        }
    }
}
