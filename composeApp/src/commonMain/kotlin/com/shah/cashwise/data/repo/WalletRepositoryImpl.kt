package com.shah.cashwise.data.repo

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOne
import com.shah.cashwise.data.mapper.toDomain
import com.shah.cashwise.db.CashWiseDatabase
import com.shah.cashwise.domain.model.Wallet
import com.shah.cashwise.domain.repo.WalletRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

/**
 * SQLDelight-backed [WalletRepository]. All database work runs on [dispatcher] —
 * SQLite calls block.
 */
class WalletRepositoryImpl(
    private val database: CashWiseDatabase,
    private val dispatcher: CoroutineDispatcher,
) : WalletRepository {

    /**
     * Combines one query per table rather than reading children inside a `map` of the
     * parent. Two reasons: SQLDelight only re-emits a query when a table *that query
     * touches* changes, so a wallet-only query would go stale on account/budget edits;
     * and it avoids an N+1 of blocking reads. [flowOn] keeps all of it off the caller's
     * thread — `mapToList(dispatcher)` only covers its own query, not the operators after it.
     */
    override val wallets: Flow<List<Wallet>> = combine(
        database.walletQueries.selectAll().asFlow().mapToList(dispatcher),
        database.accountQueries.selectAll().asFlow().mapToList(dispatcher),
        database.budgetQueries.selectAll().asFlow().mapToList(dispatcher),
    ) { walletRows, accountRows, budgetRows ->
        walletRows.map { wallet ->
            wallet.toDomain(
                accounts = accountRows.filter { it.walletId == wallet.id },
                budgets = budgetRows.filter { it.walletId == wallet.id },
            )
        }
    }.flowOn(dispatcher)

    override val hasWallet: Flow<Boolean> =
        database.walletQueries.countAll()
            .asFlow()
            .mapToOne(dispatcher)
            .map { it > 0L }
            .flowOn(dispatcher)

    override suspend fun createWallet(wallet: Wallet): Result<Unit> =
        withContext(dispatcher) {
            try {
                // One transaction: a wallet without its accounts (or a half-written
                // account list) is never observable, and a failure rolls all of it back.
                // Nothing inside may suspend — SQLDelight binds transaction state to the
                // current thread, so a suspension point here would be a correctness bug.
                database.transaction {
                    database.walletQueries.insert(
                        id = wallet.id,
                        name = wallet.name,
                        type = wallet.type.name,
                        currencyCode = wallet.currency.code,
                        createdAt = wallet.createdAt,
                    )
                    wallet.accounts.forEachIndexed { index, account ->
                        database.accountQueries.insert(
                            id = account.id,
                            walletId = wallet.id,
                            kind = account.kind.name,
                            customName = account.customName,
                            customIcon = account.customIcon?.name,
                            startingBalanceMinor = account.startingBalanceMinor,
                            position = index.toLong(),
                        )
                    }
                    wallet.budgets.forEach { budget ->
                        database.budgetQueries.insert(
                            id = budget.id,
                            walletId = wallet.id,
                            category = budget.category.name,
                            limitMinor = budget.limitMinor,
                        )
                    }
                }
                Result.success(Unit)
            } catch (cancellation: CancellationException) {
                throw cancellation // never report cancellation as a save failure
            } catch (error: Throwable) {
                Result.failure(error)
            }
        }
}
