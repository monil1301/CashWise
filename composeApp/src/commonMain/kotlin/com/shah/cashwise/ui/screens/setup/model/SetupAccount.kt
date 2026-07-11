package com.shah.cashwise.ui.screens.setup.model

import com.shah.cashwise.domain.model.AccountKind
import com.shah.cashwise.domain.model.CustomAccountIcon

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

/** The accounts shown by default on step 2 — Cash/UPI/Bank on, Card off. */
fun defaultSetupAccounts(): List<SetupAccount> = listOf(
    SetupAccount(id = "cash", kind = AccountKind.Cash, enabled = true),
    SetupAccount(id = "upi", kind = AccountKind.Upi, enabled = true),
    SetupAccount(id = "bank", kind = AccountKind.Bank, enabled = true),
    SetupAccount(id = "card", kind = AccountKind.Card, enabled = false),
)
