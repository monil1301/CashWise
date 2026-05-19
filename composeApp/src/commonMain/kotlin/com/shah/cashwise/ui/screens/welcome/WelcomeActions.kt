package com.shah.cashwise.ui.screens.welcome

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import cashwise.composeapp.generated.resources.Res
import cashwise.composeapp.generated.resources.welcome_continue_offline
import cashwise.composeapp.generated.resources.welcome_sign_in
import cashwise.composeapp.generated.resources.welcome_sign_in_hint
import com.shah.cashwise.ui.components.PrimaryButton
import com.shah.cashwise.ui.components.SecondaryButton
import org.jetbrains.compose.resources.stringResource

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
