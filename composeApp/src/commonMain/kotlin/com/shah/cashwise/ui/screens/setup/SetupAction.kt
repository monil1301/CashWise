package com.shah.cashwise.ui.screens.setup

import com.shah.cashwise.domain.model.BudgetCategory
import com.shah.cashwise.domain.model.Currency
import com.shah.cashwise.domain.model.WalletType
import com.shah.cashwise.ui.screens.setup.model.CustomAccountDraft

/** UI events emitted by [SetupScreen], handled by [SetupViewModel.onAction]. */
sealed interface SetupAction {
    /** Top-bar back button: go to the previous step, or leave setup on step 1. */
    data object Back : SetupAction

    /** Primary button: advance to the next step, or finish on the last step. */
    data object Continue : SetupAction

    data class WalletNameChanged(val name: String) : SetupAction
    data class WalletTypeChanged(val type: WalletType) : SetupAction
    data class CurrencySelected(val currency: Currency) : SetupAction

    // Step 2 — Add your accounts
    data class AccountToggled(val accountId: String, val enabled: Boolean) : SetupAction
    data class AccountBalanceChanged(val accountId: String, val balance: String) : SetupAction

    /** Confirmed the [AddCustomAccountSheet] form: append the drafted account. */
    data class CustomAccountAdded(val draft: CustomAccountDraft) : SetupAction

    // Step 3 — Lock Cashwise
    data class AppLockToggled(val enabled: Boolean) : SetupAction

    /** "Not now" on the lock step: disable app lock and advance to the next step. */
    data object AppLockSkipped : SetupAction

    /** "Set PIN" on the lock step: open the [SetPinScreen] sub-route. */
    data object OpenSetPin : SetupAction

    /** Set PIN screen confirmed a matching PIN — clear the sub-route and advance. */
    data class SetPinConfirmed(val pin: String) : SetupAction

    /** Set PIN screen was backed out of — clear the sub-route, stay on the Lock step. */
    data object SetPinDismissed : SetupAction

    // Step 4 — Set your first budget
    data class BudgetCategorySelected(val category: BudgetCategory) : SetupAction
    data class BudgetLimitChanged(val limit: String) : SetupAction

    /** "Set budget" — finish setup with the entered category + limit. */
    data object BudgetSet : SetupAction

    /** "Skip for now" — finish setup without recording a budget. */
    data object BudgetSkipped : SetupAction

    // Step 5 — Invite members (only present for [WalletType.Shared])

    /** "Invite via link" — placeholder until link sharing is wired. */
    data object InviteViaLinkClicked : SetupAction

    /** "Show QR code" — placeholder until QR presentation is wired. */
    data object ShowQrCodeClicked : SetupAction

    /** Dismiss the "Invite a member" sheet/dialog without acting. */
    data object InviteMemberDismissed : SetupAction

    /** "Done" on the invite step — finishes the shared-wallet setup. */
    data object InviteMembersDone : SetupAction
}
