package com.shah.cashwise.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountBalance
import androidx.compose.material.icons.outlined.AccountBalanceWallet
import androidx.compose.material.icons.outlined.CreditCard
import androidx.compose.material.icons.outlined.Payments
import androidx.compose.material.icons.outlined.Savings
import androidx.compose.material.icons.outlined.Smartphone
import androidx.compose.material.icons.outlined.Wallet
import androidx.compose.ui.graphics.vector.ImageVector
import cashwise.composeapp.generated.resources.Res
import cashwise.composeapp.generated.resources.setup_account_bank
import cashwise.composeapp.generated.resources.setup_account_card
import cashwise.composeapp.generated.resources.setup_account_cash
import cashwise.composeapp.generated.resources.setup_account_custom
import cashwise.composeapp.generated.resources.setup_account_upi
import com.shah.cashwise.domain.model.AccountKind
import com.shah.cashwise.domain.model.CustomAccountIcon
import org.jetbrains.compose.resources.StringResource

/**
 * How an [AccountKind] is named and drawn.
 *
 * Shared rather than co-located with a feature: setup uses it to offer accounts, and the shell
 * drawer uses it to list them. Two copies would eventually disagree, and an account that changed
 * its glyph between the screen that created it and the one that lists it is a bug users notice.
 */

/** Localized display name for an account kind. */
fun AccountKind.nameRes(): StringResource = when (this) {
    AccountKind.Cash -> Res.string.setup_account_cash
    AccountKind.Upi -> Res.string.setup_account_upi
    AccountKind.Bank -> Res.string.setup_account_bank
    AccountKind.Card -> Res.string.setup_account_card
    AccountKind.Custom -> Res.string.setup_account_custom
}

/** Glyph shown in the account's badge. */
fun AccountKind.icon(): ImageVector = when (this) {
    AccountKind.Cash -> Icons.Outlined.Wallet
    AccountKind.Upi -> Icons.Outlined.Smartphone
    AccountKind.Bank -> Icons.Outlined.AccountBalance
    AccountKind.Card -> Icons.Outlined.CreditCard
    AccountKind.Custom -> Icons.Outlined.AccountBalanceWallet
}

/** The vector glyph for a user-picked [CustomAccountIcon]. */
fun CustomAccountIcon.icon(): ImageVector = when (this) {
    CustomAccountIcon.Cash -> Icons.Outlined.Payments
    CustomAccountIcon.Bank -> Icons.Outlined.AccountBalance
    CustomAccountIcon.Card -> Icons.Outlined.CreditCard
    CustomAccountIcon.Wallet -> Icons.Outlined.AccountBalanceWallet
    CustomAccountIcon.Savings -> Icons.Outlined.Savings
    CustomAccountIcon.Pocket -> Icons.Outlined.Wallet
}
