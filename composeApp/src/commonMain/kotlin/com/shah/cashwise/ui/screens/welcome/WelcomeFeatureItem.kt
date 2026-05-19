package com.shah.cashwise.ui.screens.welcome

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.shah.cashwise.ui.components.ImagePlaceholder

/**
 * A single offline-first highlight — icon badge, title, and short description.
 */
@Composable
internal fun WelcomeFeatureItem(
    title: String,
    description: String,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        // Placeholder for the feature icon — swap for an `Icon`/`Image` later.

        Row(
            verticalAlignment = Alignment.CenterVertically,
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
