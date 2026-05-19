package com.shah.cashwise.ui.screens.welcome

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Medium-width welcome layout: the stacked compact layout, width-capped and
 * centered so the content doesn't stretch on tablets.
 */
@Composable
internal fun WelcomeMediumLayout(
    onContinueOffline: () -> Unit,
    onSignIn: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier,
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
