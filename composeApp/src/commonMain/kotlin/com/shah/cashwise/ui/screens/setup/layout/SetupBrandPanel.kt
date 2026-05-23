package com.shah.cashwise.ui.screens.setup.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cashwise.composeapp.generated.resources.Res
import cashwise.composeapp.generated.resources.app_name
import org.jetbrains.compose.resources.stringResource

/**
 * Left-hand brand panel of the expanded setup layout: the app wordmark pinned
 * near the top and the step-specific [SetupStepIllustration] centred on a
 * tinted backdrop.
 */
@Composable
internal fun SetupBrandPanel(
    currentStep: Int,
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

        SetupStepIllustration(
            currentStep = currentStep,
            modifier = Modifier.align(Alignment.Center),
        )
    }
}
