package com.shah.cashwise.app.shell

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shah.cashwise.domain.repo.SyncStatusRepository
import com.shah.cashwise.domain.repo.WalletRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update

/**
 * Backs the shell chrome (top bar + wallet picker). It owns no tab state — that lives in the
 * nav back stack.
 */
class ShellViewModel(
    walletRepository: WalletRepository,
    syncStatusRepository: SyncStatusRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(ShellState())
    val state: StateFlow<ShellState> = _state.asStateFlow()

    init {
        combine(
            walletRepository.wallets,
            syncStatusRepository.status,
        ) { wallets, syncStatus ->
            wallets to syncStatus
        }.onEach { (wallets, syncStatus) ->
            _state.update { current ->
                current.copy(
                    wallets = wallets,
                    syncStatus = syncStatus,
                    // Drop a selection whose wallet has since been deleted, so the top bar
                    // falls back to a real wallet instead of showing nothing.
                    selectedWalletId = current.selectedWalletId
                        ?.takeIf { id -> wallets.any { it.id == id } },
                )
            }
        }.launchIn(viewModelScope)
    }

    fun onAction(action: ShellAction) {
        when (action) {
            ShellAction.WalletChipClicked ->
                // Nothing to choose between with a single wallet; don't open an empty sheet.
                _state.update { it.copy(showWalletPicker = it.canSwitchWallet) }

            is ShellAction.WalletSelected ->
                _state.update {
                    it.copy(selectedWalletId = action.walletId, showWalletPicker = false)
                }

            ShellAction.WalletPickerDismissed ->
                _state.update { it.copy(showWalletPicker = false) }
        }
    }
}
