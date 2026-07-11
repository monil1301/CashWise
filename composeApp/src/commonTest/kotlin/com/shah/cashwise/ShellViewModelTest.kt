package com.shah.cashwise

import com.shah.cashwise.app.shell.ShellAction
import com.shah.cashwise.app.shell.ShellViewModel
import com.shah.cashwise.domain.model.SyncStatus
import com.shah.cashwise.domain.model.SupportedCurrencies
import com.shah.cashwise.domain.model.Wallet
import com.shah.cashwise.domain.model.WalletType
import com.shah.cashwise.domain.repo.SyncStatusRepository
import com.shah.cashwise.domain.repo.WalletRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class ShellViewModelTest {

    private val wallets = MutableStateFlow<List<Wallet>>(emptyList())
    private val syncStatus = MutableStateFlow(SyncStatus.Synced)

    private class FakeWalletRepository(
        override val wallets: Flow<List<Wallet>>,
    ) : WalletRepository {
        override val hasWallet: Flow<Boolean> = MutableStateFlow(true)
        override suspend fun createWallet(wallet: Wallet): Result<Unit> = Result.success(Unit)
    }

    private class FakeSyncStatusRepository(
        override val status: Flow<SyncStatus>,
    ) : SyncStatusRepository

    private fun viewModel() = ShellViewModel(
        walletRepository = FakeWalletRepository(wallets),
        syncStatusRepository = FakeSyncStatusRepository(syncStatus),
    )

    private fun wallet(id: String, name: String) = Wallet(
        id = id,
        name = name,
        type = WalletType.Solo,
        currency = SupportedCurrencies.first(),
        createdAt = 0L,
    )

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun showsTheFirstWalletUntilOneIsPicked() = runTest {
        wallets.value = listOf(wallet("a", "Personal"), wallet("b", "Shared"))
        val viewModel = viewModel()

        assertEquals("Personal", viewModel.state.value.selectedWallet?.name)
    }

    @Test
    fun picksTheChosenWallet() = runTest {
        wallets.value = listOf(wallet("a", "Personal"), wallet("b", "Shared"))
        val viewModel = viewModel()

        viewModel.onAction(ShellAction.WalletSelected("b"))

        assertEquals("Shared", viewModel.state.value.selectedWallet?.name)
        assertFalse(viewModel.state.value.showWalletPicker)
    }

    @Test
    fun doesNotOpenThePickerWithOnlyOneWallet() = runTest {
        wallets.value = listOf(wallet("a", "Personal"))
        val viewModel = viewModel()

        viewModel.onAction(ShellAction.WalletChipClicked)

        assertFalse(viewModel.state.value.showWalletPicker)
    }

    @Test
    fun opensThePickerWhenThereIsAChoice() = runTest {
        wallets.value = listOf(wallet("a", "Personal"), wallet("b", "Shared"))
        val viewModel = viewModel()

        viewModel.onAction(ShellAction.WalletChipClicked)

        assertTrue(viewModel.state.value.showWalletPicker)
    }

    /** A selected wallet that is later deleted must not strand the top bar with a blank name. */
    @Test
    fun fallsBackWhenTheSelectedWalletDisappears() = runTest {
        wallets.value = listOf(wallet("a", "Personal"), wallet("b", "Shared"))
        val viewModel = viewModel()
        viewModel.onAction(ShellAction.WalletSelected("b"))

        wallets.value = listOf(wallet("a", "Personal"))

        assertNull(viewModel.state.value.selectedWalletId)
        assertEquals("Personal", viewModel.state.value.selectedWallet?.name)
    }

    @Test
    fun tracksSyncStatus() = runTest {
        wallets.value = listOf(wallet("a", "Personal"))
        val viewModel = viewModel()

        syncStatus.value = SyncStatus.Offline

        assertEquals(SyncStatus.Offline, viewModel.state.value.syncStatus)
    }
}
