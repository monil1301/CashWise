package com.shah.cashwise.ui.screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cashwise.composeapp.generated.resources.Res
import cashwise.composeapp.generated.resources.app_name
import org.jetbrains.compose.resources.stringResource

/**
 * The app proper, reached once setup has produced a wallet.
 *
 * Placeholder: this is where the tabbed shell (Home / Transactions / Budgets / Insights,
 * with a nav rail on larger windows) will live.
 */
@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = stringResource(Res.string.app_name),
            style = MaterialTheme.typography.headlineSmall,
        )
    }
}
