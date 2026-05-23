package com.shah.cashwise.ui.screens.setup.steps

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import cashwise.composeapp.generated.resources.Res
import cashwise.composeapp.generated.resources.setup_lock_description
import cashwise.composeapp.generated.resources.setup_lock_pin_hint
import cashwise.composeapp.generated.resources.setup_lock_title
import com.shah.cashwise.ui.screens.setup.SetupAction
import com.shah.cashwise.ui.screens.setup.components.EnableAppLockCard
import org.jetbrains.compose.resources.stringResource

/**
 * Step 3 of setup — "Lock Cashwise": a centred lock badge, the title block, the
 * [EnableAppLockCard] toggle, and the supporting "PIN never leaves this device"
 * caption. This step renders its own header (see setupStepHasOwnHeader); the
 * action buttons live in LockStepFooter, pinned at the bottom of the layout.
 */
@Composable
internal fun LockCashwiseStep(
    appLockEnabled: Boolean,
    onAction: (SetupAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        LockStepBadge(modifier = Modifier.align(Alignment.CenterHorizontally))

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = stringResource(Res.string.setup_lock_title),
            style = MaterialTheme.typography.displaySmall,
            color = MaterialTheme.colorScheme.onBackground,
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = stringResource(Res.string.setup_lock_description),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )

        Spacer(modifier = Modifier.height(28.dp))

        EnableAppLockCard(
            enabled = appLockEnabled,
            onEnabledChange = { onAction(SetupAction.AppLockToggled(it)) },
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = stringResource(Res.string.setup_lock_pin_hint),
            style = MaterialTheme.typography.bodySmall,
            fontStyle = FontStyle.Italic,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}
