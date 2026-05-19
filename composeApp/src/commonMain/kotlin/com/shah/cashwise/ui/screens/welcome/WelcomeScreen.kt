package com.shah.cashwise.ui.screens.welcome

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shah.cashwise.ui.theme.CashWiseTheme

private enum class WelcomeLayoutType {
    Compact,
    Medium,
    Expanded,
}

/**
 * Offline-first entry screen shown after onboarding. Lets the user start
 * locally ("Continue Offline") or sign in to enable sync.
 */
@Composable
fun WelcomeScreen(
    onContinueOffline: () -> Unit,
    onSignIn: () -> Unit,
    modifier: Modifier = Modifier,
) {
    BoxWithConstraints(modifier = modifier.fillMaxSize()) {
        val layoutType = when {
            maxWidth < 600.dp -> WelcomeLayoutType.Compact
            maxWidth < 840.dp -> WelcomeLayoutType.Medium
            else -> WelcomeLayoutType.Expanded
        }

        when (layoutType) {
            WelcomeLayoutType.Compact -> {
                WelcomeCompactLayout(
                    onContinueOffline = onContinueOffline,
                    onSignIn = onSignIn,
                    modifier = Modifier.fillMaxSize(),
                )
            }

            WelcomeLayoutType.Medium -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.TopCenter,
                ) {
                    WelcomeCompactLayout(
                        onContinueOffline = onContinueOffline,
                        onSignIn = onSignIn,
                        modifier = Modifier
                            .fillMaxSize()
                            .widthIn(max = 560.dp),
                    )
                }
            }

            WelcomeLayoutType.Expanded -> {
                WelcomeExpandedLayout(
                    onContinueOffline = onContinueOffline,
                    onSignIn = onSignIn,
                    modifier = Modifier.fillMaxSize(),
                )
            }
        }
    }
}

@Preview(name = "Welcome Compact", widthDp = 390, heightDp = 844)
@Composable
private fun WelcomeScreenCompactPreview() {
    CashWiseTheme {
        WelcomeScreen(onContinueOffline = {}, onSignIn = {})
    }
}

@Preview(name = "Welcome Expanded", widthDp = 1200, heightDp = 900)
@Composable
private fun WelcomeScreenExpandedPreview() {
    CashWiseTheme {
        WelcomeScreen(onContinueOffline = {}, onSignIn = {})
    }
}
