package com.shah.cashwise.ui.screens.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

/**
 * Rounded illustration card. The drawable already carries the dark artwork and
 * background, so the card simply clips and crops the image to its bounds.
 */
@Composable
internal fun OnboardingIllustrationCard(
    page: OnboardingPage,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(28.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
    ) {
        Image(
            painter = painterResource(page.image),
            contentDescription = stringResource(page.title),
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
        )
    }
}

/**
 * Headline + supporting text for a single onboarding page. Centered by default
 * (compact/medium); the expanded layout passes [Alignment.Start] to left-align.
 */
@Composable
internal fun OnboardingMessage(
    page: OnboardingPage,
    modifier: Modifier = Modifier,
    horizontalAlignment: Alignment.Horizontal = Alignment.CenterHorizontally,
    textAlign: TextAlign = TextAlign.Center,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = horizontalAlignment,
    ) {
        // minLines keeps every page's message block the same height, so
        // shorter titles/descriptions don't shift the layout while swiping.
        Text(
            text = stringResource(page.title),
            style = MaterialTheme.typography.displayLarge,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = textAlign,
            minLines = 2,
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = stringResource(page.description),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = textAlign,
            minLines = 2,
        )
    }
}
