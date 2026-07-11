package com.shah.cashwise.ui.screens.signin

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shah.cashwise.ui.screens.signin.layout.SignInCompactLayout
import com.shah.cashwise.ui.theme.CashWiseTheme
import org.koin.compose.viewmodel.koinViewModel

private enum class SignInLayoutType {
    Compact,
    Medium,
    Expanded,
}

/** Width cap applied to the compact stack on larger windows so it stays readable. */
private val RegularContentMaxWidth = 480.dp

/**
 * "Sign in to Sync" — the optional account screen reached from the offline-first
 * welcome screen. Nothing here gates the app, so [onBack] always returns the user
 * to the offline path. Successful sign-in is observed by the app shell via the
 * session flow (which dismisses this screen); only Back is routed through here.
 */
@Composable
fun SignInScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val viewModel = koinViewModel<SignInViewModel>()
    val state by viewModel.state.collectAsState()
    val onBackState by rememberUpdatedState(onBack)

    val handleAction: (SignInAction) -> Unit = { action ->
        when (viewModel.onAction(action)) {
            SignInNavResult.ExitToCaller -> onBackState()
            null -> Unit
        }
    }

    BoxWithConstraints(modifier = modifier.fillMaxSize()) {
        val layoutType = when {
            maxWidth < 600.dp -> SignInLayoutType.Compact
            maxWidth < 840.dp -> SignInLayoutType.Medium
            else -> SignInLayoutType.Expanded
        }

        when (layoutType) {
            SignInLayoutType.Compact -> SignInCompactLayout(
                state = state,
                onAction = handleAction,
                modifier = Modifier.fillMaxSize(),
            )

            // Medium/Expanded: no dedicated split design yet — reuse the compact
            // stack, centred and width-capped to avoid stretched controls.
            SignInLayoutType.Medium,
            SignInLayoutType.Expanded -> Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.TopCenter,
            ) {
                SignInCompactLayout(
                    state = state,
                    onAction = handleAction,
                    modifier = Modifier
                        .fillMaxHeight()
                        .widthIn(max = RegularContentMaxWidth),
                )
            }
        }
    }
}

// Previews render the layout directly with sample state so they don't require Koin.

@Preview(name = "Sign In — Idle", widthDp = 390, heightDp = 844)
@Composable
private fun SignInScreenIdlePreview() {
    CashWiseTheme {
        SignInCompactLayout(
            state = SignInState(),
            onAction = {},
            modifier = Modifier.fillMaxSize(),
        )
    }
}

@Preview(name = "Sign In — Loading", widthDp = 390, heightDp = 844)
@Composable
private fun SignInScreenLoadingPreview() {
    CashWiseTheme {
        SignInCompactLayout(
            state = SignInState(loadingProvider = AuthProvider.Google),
            onAction = {},
            modifier = Modifier.fillMaxSize(),
        )
    }
}
