package com.shah.cashwise.data.mapper

import com.shah.cashwise.domain.model.Account
import com.shah.cashwise.domain.model.AccountKind
import com.shah.cashwise.domain.model.Budget
import com.shah.cashwise.domain.model.BudgetCategory
import com.shah.cashwise.domain.model.CustomAccountIcon
import com.shah.cashwise.domain.model.DefaultCurrency
import com.shah.cashwise.domain.model.SupportedCurrencies
import com.shah.cashwise.domain.model.Wallet
import com.shah.cashwise.domain.model.WalletType
import com.shah.cashwise.db.Account as AccountRow
import com.shah.cashwise.db.Budget as BudgetRow
import com.shah.cashwise.db.Wallet as WalletRow

/**
 * Maps SQLDelight rows to domain models. Enums are stored by [Enum.name] and
 * currencies by ISO code; anything unrecognised (an older build, a hand-edited
 * db) falls back to a sane default rather than throwing, so a single bad row
 * can't make the app unopenable.
 */
internal fun WalletRow.toDomain(
    accounts: List<AccountRow>,
    budgets: List<BudgetRow>,
): Wallet = Wallet(
    id = id,
    name = name,
    type = WalletType.entries.firstOrNull { it.name == type } ?: WalletType.Solo,
    currency = SupportedCurrencies.firstOrNull { it.code == currencyCode } ?: DefaultCurrency,
    createdAt = createdAt,
    accounts = accounts.map { it.toDomain() },
    budgets = budgets.mapNotNull { it.toDomainOrNull() },
)

internal fun AccountRow.toDomain(): Account = Account(
    id = id,
    kind = AccountKind.entries.firstOrNull { it.name == kind } ?: AccountKind.Custom,
    startingBalanceMinor = startingBalanceMinor,
    customName = customName,
    customIcon = customIcon?.let { stored ->
        CustomAccountIcon.entries.firstOrNull { it.name == stored }
    },
)

/**
 * An unrecognised category is dropped rather than coerced: silently relabelling a limit
 * as [BudgetCategory.FoodAndDining] would show the user real money under the wrong
 * category, which is worse than not showing the budget at all.
 */
internal fun BudgetRow.toDomainOrNull(): Budget? {
    val known = BudgetCategory.entries.firstOrNull { it.name == category } ?: return null
    return Budget(id = id, category = known, limitMinor = limitMinor)
}
