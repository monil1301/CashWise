package com.shah.cashwise.ui.screens.welcome

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cashwise.composeapp.generated.resources.Res
import cashwise.composeapp.generated.resources.welcome_illustration_placeholder
import com.shah.cashwise.ui.components.ImagePlaceholder
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
