package com.shah.cashwise.ui.screens.welcome

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cashwise.composeapp.generated.resources.Res
import cashwise.composeapp.generated.resources.welcome_feature_local_data_description
import cashwise.composeapp.generated.resources.welcome_feature_local_data_title
import cashwise.composeapp.generated.resources.welcome_feature_offline_first_description
import cashwise.composeapp.generated.resources.welcome_feature_offline_first_title
import org.jetbrains.compose.resources.stringResource

/**
 * Row of [WelcomeFeatureItem]s highlighting the offline-first selling points.
 */
@Composable
internal fun WelcomeFeatures(
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
