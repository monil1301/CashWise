package com.shah.cashwise.ui.screens.welcome

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

/**
 * Stacked welcome layout for phones (also reused, width-capped, for Medium).
 * The headline block sits at the top and the actions block at the bottom,
 * with flexible space between them.
 */
@Composable
internal fun WelcomeCompactLayout(
    onContinueOffline: () -> Unit,
    onSignIn: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(128.dp))

        WelcomeHeader(
            horizontalAlignment = Alignment.CenterHorizontally,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
        )

        // Flexible gap pushing the actions to the bottom of the screen,
        // while still guaranteeing breathing room on short displays.
        Spacer(modifier = Modifier.heightIn(min = 36.dp).weight(1f))

        WelcomeActions(
            onContinueOffline = onContinueOffline,
            onSignIn = onSignIn,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(modifier = Modifier.height(24.dp))
    }
}
