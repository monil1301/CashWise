package com.shah.cashwise.app.shell

import com.shah.cashwise.domain.model.SyncStatus
import com.shah.cashwise.domain.model.Wallet

/**
 * What the shell chrome needs to draw itself: which wallet is current, what else the user
 * could switch to, and how sync is doing.
 *
 * The tab selection is *not* here — it belongs to the nav back stack, and duplicating it in
 * state would create a second source of truth that could disagree with the graph.
 */
data class ShellState(
    val wallets: List<Wallet> = emptyList(),
    val selectedWalletId: String? = null,
    val syncStatus: SyncStatus = SyncStatus.Synced,
    val showWalletPicker: Boolean = false,
) {

    /**
     * The wallet whose name the top bar shows. Falls back to the first wallet, so the bar is
     * never blank in the window between the wallets arriving and a selection being made.
     */
    val selectedWallet: Wallet?
        get() = wallets.firstOrNull { it.id == selectedWalletId } ?: wallets.firstOrNull()

    /** Only worth offering the picker when there is something to pick between. */
    val canSwitchWallet: Boolean
        get() = wallets.size > 1
}
