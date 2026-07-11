package com.shah.cashwise.app.shell

/** Everything the shell chrome can ask for. Screen content raises its own actions. */
sealed interface ShellAction {

    /** The wallet chip in the top bar was tapped. */
    data object WalletChipClicked : ShellAction

    /** A wallet was chosen from the picker. */
    data class WalletSelected(val walletId: String) : ShellAction

    /** The picker was dismissed without choosing. */
    data object WalletPickerDismissed : ShellAction
}
