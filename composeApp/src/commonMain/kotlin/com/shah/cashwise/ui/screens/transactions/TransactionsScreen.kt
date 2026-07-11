package com.shah.cashwise.ui.screens.transactions

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cashwise.composeapp.generated.resources.Res
import cashwise.composeapp.generated.resources.transactions_placeholder
import com.shah.cashwise.ui.components.ScreenPlaceholder
import org.jetbrains.compose.resources.stringResource

/** TODO(transactions): placeholder tab. The shell routes to it; the feature is not built yet. */
@Composable
fun TransactionsScreen(modifier: Modifier = Modifier) {
    ScreenPlaceholder(
        title = stringResource(Res.string.transactions_placeholder),
        modifier = modifier,
    )
}
