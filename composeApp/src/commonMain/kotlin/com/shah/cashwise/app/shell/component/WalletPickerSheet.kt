package com.shah.cashwise.app.shell.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.outlined.AccountBalanceWallet
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cashwise.composeapp.generated.resources.Res
import cashwise.composeapp.generated.resources.shell_wallet_picker_title
import com.shah.cashwise.domain.model.Wallet
import org.jetbrains.compose.resources.stringResource

/**
 * Lets the user switch which wallet the app is showing.
 *
 * Only opened when more than one wallet exists — see `ShellViewModel`. The choice is currently
 * in-memory: it resets on relaunch, because "which wallet was I looking at" is a UI preference
 * with nowhere durable to live yet.
 *
 * TODO(wallets): persist the selection via AppPreferencesRepository once multi-wallet creation
 *  exists — until then there is only ever one wallet to select and nothing to remember.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WalletPickerSheet(
    wallets: List<Wallet>,
    selectedWalletId: String?,
    onWalletSelected: (String) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = rememberModalBottomSheetState(),
        modifier = modifier,
    ) {
        Column(modifier = Modifier.navigationBarsPadding()) {
            Text(
                text = stringResource(Res.string.shell_wallet_picker_title),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp),
            )

            wallets.forEach { wallet ->
                ListItem(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(onClick = { onWalletSelected(wallet.id) }),
                    headlineContent = { Text(wallet.name) },
                    leadingContent = {
                        Icon(
                            imageVector = Icons.Outlined.AccountBalanceWallet,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                        )
                    },
                    trailingContent = {
                        if (wallet.id == selectedWalletId) {
                            Icon(
                                imageVector = Icons.Filled.Check,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                            )
                        }
                    },
                )
            }
        }
    }
}
