package com.shah.cashwise.ui.screens.setup.layout

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cashwise.composeapp.generated.resources.Res
import cashwise.composeapp.generated.resources.back
import cashwise.composeapp.generated.resources.setup_step_label
import com.shah.cashwise.ui.components.SegmentedProgressIndicator
import org.jetbrains.compose.resources.stringResource

/**
 * Common setup top bar — back button and "Step X of Y" label on one row, with
 * the [SegmentedProgressIndicator] beneath. Shared by every setup step.
 */
@Composable
internal fun SetupTopBar(
    currentStep: Int,
    totalSteps: Int,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(Res.string.back),
                    tint = MaterialTheme.colorScheme.onSurface,
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = stringResource(Res.string.setup_step_label, currentStep + 1, totalSteps)
                    .uppercase(),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        SegmentedProgressIndicator(
            totalSteps = totalSteps,
            currentStep = currentStep,
        )
    }
}
