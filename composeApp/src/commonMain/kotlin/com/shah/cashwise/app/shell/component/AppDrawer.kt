package com.shah.cashwise.app.shell.component

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination
import cashwise.composeapp.generated.resources.Res
import cashwise.composeapp.generated.resources.shell_accounts
import cashwise.composeapp.generated.resources.shell_add_transaction
import com.shah.cashwise.app.shell.ShellState
import com.shah.cashwise.app.shell.ShellTab
import com.shah.cashwise.app.shell.isSelected
import com.shah.cashwise.core.utils.formatMinorUnits
import com.shah.cashwise.domain.model.Account
import com.shah.cashwise.domain.model.Currency
import com.shah.cashwise.domain.model.SyncStatus
import com.shah.cashwise.ui.components.SyncStatusPill
import com.shah.cashwise.ui.components.icon
import com.shah.cashwise.ui.components.labelRes
import com.shah.cashwise.ui.components.nameRes
import org.jetbrains.compose.resources.stringResource

/** Wide enough for the account rows' "Bank ₹1,24,300" without wrapping. */
val DrawerWidth = 300.dp

/**
 * The permanent side drawer for the Expanded breakpoint.
 *
 * Carries everything the phone splits across its top bar and bottom bar — wallet, tabs, add
 * button — plus the account balances, which there is finally room for. The tabs are the same
 * [ShellTab.entries] the phone's bottom bar renders, so the two can't disagree about what the
 * app's top-level destinations are.
 */
@Composable
fun AppDrawer(
    state: ShellState,
    currentDestination: NavDestination?,
    onTabClick: (ShellTab) -> Unit,
    onWalletClick: () -> Unit,
    onAddClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier
            .width(DrawerWidth)
            .fillMaxHeight(),
        color = MaterialTheme.colorScheme.surface,
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 20.dp),
        ) {
            DrawerWalletCard(
                state = state,
                onClick = onWalletClick,
            )

            Spacer(Modifier.height(16.dp))

            Button(
                onClick = onAddClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                ),
            ) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text(
                    text = stringResource(Res.string.shell_add_transaction),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                )
            }

            Spacer(Modifier.height(20.dp))

            ShellTab.entries.forEach { tab ->
                val selected = tab.isSelected(currentDestination)
                NavigationDrawerItem(
                    selected = selected,
                    onClick = { onTabClick(tab) },
                    icon = {
                        Icon(
                            imageVector = if (selected) tab.selectedIcon else tab.unselectedIcon,
                            contentDescription = null, // the label is the accessible name
                        )
                    },
                    label = {
                        Text(
                            text = stringResource(tab.label),
                            style = MaterialTheme.typography.titleSmall,
                        )
                    },
                    shape = RoundedCornerShape(28.dp),
                    colors = NavigationDrawerItemDefaults.colors(
                        selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                        selectedIconColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        selectedTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        unselectedContainerColor = MaterialTheme.colorScheme.surface,
                        unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        unselectedTextColor = MaterialTheme.colorScheme.onSurface,
                    ),
                    modifier = Modifier.padding(vertical = 2.dp),
                )
            }

            val wallet = state.selectedWallet
            if (wallet != null && wallet.accounts.isNotEmpty()) {
                Spacer(Modifier.height(16.dp))
                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                Spacer(Modifier.height(12.dp))

                Text(
                    text = stringResource(Res.string.shell_accounts).uppercase(),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = 0.8.sp,
                    modifier = Modifier.padding(horizontal = 12.dp),
                )

                Spacer(Modifier.height(8.dp))

                wallet.accounts.forEach { account ->
                    DrawerAccountRow(account = account, currency = wallet.currency)
                }
            }

            Spacer(Modifier.height(24.dp))

            // Renders nothing while synced — the dot on the wallet card says so instead.
            SyncStatusPill(
                status = state.syncStatus,
                modifier = Modifier.padding(horizontal = 4.dp),
            )
        }
    }
}

/** Wallet identity: avatar with the synced dot, name, and the wallet's type underneath. */
@Composable
private fun DrawerWalletCard(
    state: ShellState,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val wallet = state.selectedWallet ?: return

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outlineVariant,
                shape = RoundedCornerShape(16.dp),
            )
            .then(if (state.canSwitchWallet) Modifier.clickable(onClick = onClick) else Modifier)
            .padding(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        WalletAvatar(showSyncedDot = state.syncStatus == SyncStatus.Synced)

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = wallet.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Text(
                text = stringResource(wallet.type.labelRes()),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }

        if (state.canSwitchWallet) {
            Icon(
                imageVector = Icons.Filled.KeyboardArrowDown,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

/**
 * One account and its balance.
 *
 * The balance is the account's *starting* balance: transactions do not exist yet, so nothing
 * has moved it. A negative balance (an overdrawn card) is coloured with the theme's error
 * colour — the sign alone is easy to miss in a dense list.
 */
@Composable
private fun DrawerAccountRow(
    account: Account,
    currency: Currency,
    modifier: Modifier = Modifier,
) {
    val negative = account.startingBalanceMinor < 0

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = account.customIcon?.icon() ?: account.kind.icon(),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(20.dp),
        )

        Text(
            text = account.customName ?: stringResource(account.kind.nameRes()),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f),
        )

        Text(
            text = formatMinorUnits(account.startingBalanceMinor, currency.symbol),
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold,
            color = if (negative) {
                MaterialTheme.colorScheme.error
            } else {
                MaterialTheme.colorScheme.onSurface
            },
            maxLines = 1,
        )
    }
}
