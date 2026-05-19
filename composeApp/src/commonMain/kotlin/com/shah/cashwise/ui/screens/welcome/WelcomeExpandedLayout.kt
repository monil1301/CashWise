package com.shah.cashwise.ui.screens.welcome

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

/**
 * Split welcome layout for tablet-landscape / desktop: full-bleed illustration
 * panel on the left, copy and actions on the right.
 */
@Composable
internal fun WelcomeExpandedLayout(
    onContinueOffline: () -> Unit,
    onSignIn: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .background(MaterialTheme.colorScheme.primaryContainer),
            contentAlignment = Alignment.Center,
        ) {
            WelcomeIllustration(
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .fillMaxHeight(0.8f),
            )
        }

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .padding(horizontal = 56.dp, vertical = 40.dp),
            contentAlignment = Alignment.Center,
        ) {
            Column(modifier = Modifier.widthIn(max = 440.dp)) {
                WelcomeHeader(
                    horizontalAlignment = Alignment.Start,
                    textAlign = TextAlign.Start,
                    showIllustration = false,
                    descriptionHorizontalPadding = 0.dp,
                    modifier = Modifier.fillMaxWidth(),
                )

                Spacer(modifier = Modifier.height(40.dp))

                WelcomeActions(
                    onContinueOffline = onContinueOffline,
                    onSignIn = onSignIn,
                    textAlign = TextAlign.Start,
                    hintTopSpacing = 32.dp,
                    dividerTopSpacing = 60.dp,
                    featuresTopSpacing = 24.dp,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }
    }
}
