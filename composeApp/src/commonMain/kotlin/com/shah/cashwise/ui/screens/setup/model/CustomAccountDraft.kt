package com.shah.cashwise.ui.screens.setup.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountBalance
import androidx.compose.material.icons.outlined.AccountBalanceWallet
import androidx.compose.material.icons.outlined.CreditCard
import androidx.compose.material.icons.outlined.Payments
import androidx.compose.material.icons.outlined.Savings
import androidx.compose.material.icons.outlined.Wallet
import androidx.compose.ui.graphics.vector.ImageVector

/** A glyph offered by the add-custom-account icon picker. */
enum class CustomAccountIcon {
    Cash,
    Bank,
    Card,
    Wallet,
    Savings,
    Pocket,
}

/** The vector glyph for a [CustomAccountIcon]. */
fun CustomAccountIcon.icon(): ImageVector = when (this) {
    CustomAccountIcon.Cash -> Icons.Outlined.Payments
    CustomAccountIcon.Bank -> Icons.Outlined.AccountBalance
    CustomAccountIcon.Card -> Icons.Outlined.CreditCard
    CustomAccountIcon.Wallet -> Icons.Outlined.AccountBalanceWallet
    CustomAccountIcon.Savings -> Icons.Outlined.Savings
    CustomAccountIcon.Pocket -> Icons.Outlined.Wallet
}

/**
 * The form result emitted when the user confirms the add-custom-account form.
 * [startingBalance] is the raw text typed (may be blank — the field is optional).
 */
data class CustomAccountDraft(
    val name: String,
    val icon: CustomAccountIcon,
    val startingBalance: String,
)
