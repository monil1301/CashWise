package com.shah.cashwise.ui.screens.setpin.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cashwise.composeapp.generated.resources.Res
import cashwise.composeapp.generated.resources.app_name
import cashwise.composeapp.generated.resources.set_pin_brand_description
import cashwise.composeapp.generated.resources.set_pin_brand_tagline
import com.shah.cashwise.ui.screens.setpin.illustration.SetPinSecurityIllustration
import org.jetbrains.compose.resources.stringResource

/**
 * Left-hand brand panel of [SetPinExpandedLayout] — the app wordmark pinned
 * near the top, the [SetPinSecurityIllustration] centred on the tinted backdrop,
 * and a "security is our priority" tagline + description directly beneath.
 */
@Composable
internal fun SetPinBrandPanel(
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier.background(MaterialTheme.colorScheme.primaryContainer)) {
        Text(
            text = stringResource(Res.string.app_name),
            style = MaterialTheme.typography.displayMedium,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 56.dp),
        )

        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            SetPinSecurityIllustration()

            Spacer(modifier = Modifier.height(28.dp))

            Text(
                text = stringResource(Res.string.set_pin_brand_tagline),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center,
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = stringResource(Res.string.set_pin_brand_description),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
            )
        }
    }
}
