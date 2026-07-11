package com.shah.cashwise.domain.model

/**
 * A wallet and everything created with it. The wallet is the aggregate root:
 * [accounts] and [budgets] belong to it and are deleted with it.
 *
 * Monetary values are **minor units** (e.g. paise) so arithmetic stays exact —
 * see [Account.startingBalanceMinor] and [Budget.limitMinor].
 */
data class Wallet(
    val id: String,
    val name: String,
    val type: WalletType,
    val currency: Currency,
    val createdAt: Long,
    val accounts: List<Account> = emptyList(),
    val budgets: List<Budget> = emptyList(),
)

/**
 * A money account inside a wallet. [customName] and [customIcon] are only set
 * when [kind] is [AccountKind.Custom]; the preset kinds take their name and
 * glyph from the kind itself.
 */
data class Account(
    val id: String,
    val kind: AccountKind,
    val startingBalanceMinor: Long,
    val customName: String? = null,
    val customIcon: CustomAccountIcon? = null,
)

/** A spending limit for one [category] within a wallet. */
data class Budget(
    val id: String,
    val category: BudgetCategory,
    val limitMinor: Long,
)
