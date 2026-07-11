package com.shah.cashwise.ui.screens.setup.layout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cashwise.composeapp.generated.resources.Res
import cashwise.composeapp.generated.resources.setup_save_failed
import cashwise.composeapp.generated.resources.setup_saving
import com.shah.cashwise.ui.screens.setup.SetupState
import org.jetbrains.compose.resources.stringResource

/**
 * Feedback for the final "finish setup" write, shown just above the footer.
 *
 * Finishing setup is a database write, not an instant transition — without this the
 * user taps the last button and the screen appears to do nothing, which is exactly
 * what provokes a second tap (and, before the completion flag was derived from the
 * database, a second wallet). On failure it says so, and the footer stays enabled so
 * the write can be retried.
 */
@Composable
internal fun SetupSaveStatus(
    state: SetupState,
    modifier: Modifier = Modifier,
) {
    when {
        state.isSaving -> Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            CircularProgressIndicator(
                strokeWidth = 2.dp,
                modifier = Modifier.size(16.dp),
            )
            Text(
                text = stringResource(Res.string.setup_saving),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }

        state.saveFailed -> Text(
            text = stringResource(Res.string.setup_save_failed),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.error,
            textAlign = TextAlign.Center,
            modifier = modifier.fillMaxWidth(),
        )
    }
}
