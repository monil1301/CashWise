package com.shah.cashwise.ui.screens.setup.layout

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cashwise.composeapp.generated.resources.Res
import cashwise.composeapp.generated.resources.setup_step_label
import com.shah.cashwise.ui.components.SegmentedProgressIndicator
import org.jetbrains.compose.resources.stringResource

/**
 * Step progress for the expanded layout — an uppercase "STEP X OF Y" label
 * above the [SegmentedProgressIndicator]. Unlike [SetupTopBar] it carries no
 * back button (the expanded layout has no top bar).
 */
@Composable
internal fun SetupStepProgress(
    currentStep: Int,
    totalSteps: Int,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(Res.string.setup_step_label, currentStep + 1, totalSteps)
                .uppercase(),
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )

        Spacer(modifier = Modifier.height(10.dp))

        SegmentedProgressIndicator(
            totalSteps = totalSteps,
            currentStep = currentStep,
        )
    }
}
