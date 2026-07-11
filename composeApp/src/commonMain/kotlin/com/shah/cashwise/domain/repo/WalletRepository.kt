package com.shah.cashwise.domain.repo

import com.shah.cashwise.domain.model.Wallet
import kotlinx.coroutines.flow.Flow

/**
 * Persistence boundary for wallets. The wallet is the aggregate root, so a wallet
 * and its accounts/budgets are written together — [createWallet] is atomic, and
 * either the whole wallet lands or none of it does.
 *
 * Offline-first: this is local storage only. Sync is a separate concern behind
 * [com.shah.cashwise.core.utils.FeatureFlags.SYNC_ENABLED].
 */
interface WalletRepository {

    /** Emits the stored wallets (with their accounts and budgets), newest last. */
    val wallets: Flow<List<Wallet>>

    /**
     * Whether any wallet exists. This is the app's definition of "setup is done":
     * the wallet is the durable artefact setup produces, so deriving completion from it
     * keeps a single source of truth. A separate "completed" flag in another store could
     * disagree with the database — e.g. the wallet commits but the flag never lands, and
     * the user redoes setup and ends up with two wallets.
     */
    val hasWallet: Flow<Boolean>

    /**
     * Persists [wallet] together with its accounts and budgets in one transaction.
     * The caller supplies the ids and [Wallet.createdAt] so this stays pure and testable.
     */
    suspend fun createWallet(wallet: Wallet): Result<Unit>
}
