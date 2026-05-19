package com.shah.cashwise.ui.screens.welcome

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import cashwise.composeapp.generated.resources.Res
import cashwise.composeapp.generated.resources.welcome_continue_offline
import cashwise.composeapp.generated.resources.welcome_description
import cashwise.composeapp.generated.resources.welcome_feature_local_data_description
import cashwise.composeapp.generated.resources.welcome_feature_local_data_title
import cashwise.composeapp.generated.resources.welcome_feature_offline_first_description
import cashwise.composeapp.generated.resources.welcome_feature_offline_first_title
import cashwise.composeapp.generated.resources.welcome_illustration_placeholder
import cashwise.composeapp.generated.resources.welcome_sign_in
import cashwise.composeapp.generated.resources.welcome_sign_in_hint
import cashwise.composeapp.generated.resources.welcome_title
import com.shah.cashwise.ui.components.ImagePlaceholder
import com.shah.cashwise.ui.components.PrimaryButton
import com.shah.cashwise.ui.components.SecondaryButton
import org.jetbrains.compose.resources.stringResource

/**
 * Illustration slot for the welcome screen. Currently a placeholder — replace
 * the [ImagePlaceholder] body with an `Image` once the artwork is available.
 * The caller sizes it: a small centered badge in compact, a full-bleed panel
 * in expanded.
 */
@Composable
internal fun WelcomeIllustration(
    modifier: Modifier = Modifier,
) {
    ImagePlaceholder(
        modifier = modifier,
        label = stringResource(Res.string.welcome_illustration_placeholder),
    )
}

/**
 * Headline block — illustration badge, title, and supporting description.
 * Compact pins this to the top of the screen; expanded omits the badge
 * ([showIllustration] = false) because the artwork is its own side panel.
 *
 * [descriptionHorizontalPadding] narrows the description so centered copy
 * wraps tidily; left-aligned (expanded) callers pass `0.dp` to keep it flush
 * with the title.
 */
@Composable
internal fun WelcomeHeader(
    horizontalAlignment: Alignment.Horizontal,
    textAlign: TextAlign,
    modifier: Modifier = Modifier,
    showIllustration: Boolean = true,
    descriptionHorizontalPadding: Dp = 32.dp,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = horizontalAlignment,
    ) {
        if (showIllustration) {
            WelcomeIllustration(modifier = Modifier.size(96.dp))
            Spacer(modifier = Modifier.height(28.dp))
        }

        // Display — screen headline (32sp / 44sp)
        Text(
            text = stringResource(Res.string.welcome_title),
            style = MaterialTheme.typography.displayLarge,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = textAlign,
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Body — supporting description (14sp / 16sp)
        Text(
            text = stringResource(Res.string.welcome_description),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = textAlign,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = descriptionHorizontalPadding),
        )
    }
}

/**
 * Actions block — primary/secondary buttons, the sign-in hint, and the
 * feature highlights below a divider. Compact pins this to the bottom of
 * the screen; expanded stacks it under [WelcomeHeader].
 *
 * [hintTopSpacing], [dividerTopSpacing], and [featuresTopSpacing] tune the
 * gaps below the buttons — expanded passes larger values for a more open
 * layout; compact uses the defaults.
 */
@Composable
internal fun WelcomeActions(
    onContinueOffline: () -> Unit,
    onSignIn: () -> Unit,
    textAlign: TextAlign,
    modifier: Modifier = Modifier,
    hintTopSpacing: Dp = 20.dp,
    dividerTopSpacing: Dp = 20.dp,
    featuresTopSpacing: Dp = 20.dp,
) {
    Column(modifier = modifier) {
        PrimaryButton(
            text = stringResource(Res.string.welcome_continue_offline),
            onClick = onContinueOffline,
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(modifier = Modifier.height(12.dp))

        SecondaryButton(
            text = stringResource(Res.string.welcome_sign_in),
            onClick = onSignIn,
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(modifier = Modifier.height(hintTopSpacing))

        // Caption — helper text (11sp)
        Text(
            text = stringResource(Res.string.welcome_sign_in_hint),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = textAlign,
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(modifier = Modifier.height(dividerTopSpacing))

        HorizontalDivider(color = MaterialTheme.colorScheme.outline)

        Spacer(modifier = Modifier.height(featuresTopSpacing))

        WelcomeFeatures()
    }
}

@Composable
private fun WelcomeFeatures(
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(24.dp),
    ) {
        WelcomeFeatureItem(
            title = stringResource(Res.string.welcome_feature_local_data_title),
            description = stringResource(Res.string.welcome_feature_local_data_description),
            modifier = Modifier.weight(1f),
        )
        WelcomeFeatureItem(
            title = stringResource(Res.string.welcome_feature_offline_first_title),
            description = stringResource(Res.string.welcome_feature_offline_first_description),
            modifier = Modifier.weight(1f),
        )
    }
}

@Composable
private fun WelcomeFeatureItem(
    title: String,
    description: String,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        // Placeholder for the feature icon — swap for an `Icon`/`Image` later.

        Row (
            verticalAlignment = Alignment.CenterVertically
        ) {
            ImagePlaceholder(
                modifier = Modifier.size(24.dp),
                shape = MaterialTheme.shapes.small,
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = title,
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurface,
            )
        }

        Spacer(modifier = Modifier.height(2.dp))

        Text(
            text = description,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}
