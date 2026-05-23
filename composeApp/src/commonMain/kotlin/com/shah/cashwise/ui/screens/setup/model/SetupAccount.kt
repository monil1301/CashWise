package com.shah.cashwise.ui.screens.setup.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountBalance
import androidx.compose.material.icons.outlined.AccountBalanceWallet
import androidx.compose.material.icons.outlined.CreditCard
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
import org.jetbrains.compose.resources.StringResource

/**
 * An account row in setup step 2. [startingBalance] is the raw text the user
 * typed (optional) and is only meaningful while [enabled].
 *
 * For [AccountKind.Custom] accounts, [customName] holds the user-entered name
 * and [customIcon] the picked glyph; both are unused for the preset kinds.
 */
data class SetupAccount(
    val id: String,
    val kind: AccountKind,
    val enabled: Boolean,
    val startingBalance: String = "",
    val customName: String = "",
    val customIcon: CustomAccountIcon? = null,
)

/** Localized display name for an account kind. */
fun AccountKind.nameRes(): StringResource = when (this) {
    AccountKind.Cash -> Res.string.setup_account_cash
    AccountKind.Upi -> Res.string.setup_account_upi
    AccountKind.Bank -> Res.string.setup_account_bank
    AccountKind.Card -> Res.string.setup_account_card
    AccountKind.Custom -> Res.string.setup_account_custom
}

/** Glyph shown in the account row's badge. */
fun AccountKind.icon(): ImageVector = when (this) {
    AccountKind.Cash -> Icons.Outlined.Wallet
    AccountKind.Upi -> Icons.Outlined.Smartphone
    AccountKind.Bank -> Icons.Outlined.AccountBalance
    AccountKind.Card -> Icons.Outlined.CreditCard
    AccountKind.Custom -> Icons.Outlined.AccountBalanceWallet
}

/** The accounts shown by default on step 2 — Cash/UPI/Bank on, Card off. */
fun defaultSetupAccounts(): List<SetupAccount> = listOf(
    SetupAccount(id = "cash", kind = AccountKind.Cash, enabled = true),
    SetupAccount(id = "upi", kind = AccountKind.Upi, enabled = true),
    SetupAccount(id = "bank", kind = AccountKind.Bank, enabled = true),
    SetupAccount(id = "card", kind = AccountKind.Card, enabled = false),
)
