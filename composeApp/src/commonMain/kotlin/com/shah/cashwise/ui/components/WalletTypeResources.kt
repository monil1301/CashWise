package com.shah.cashwise.ui.components

import cashwise.composeapp.generated.resources.Res
import cashwise.composeapp.generated.resources.shell_wallet_type_shared
import cashwise.composeapp.generated.resources.shell_wallet_type_solo
import com.shah.cashwise.domain.model.WalletType
import org.jetbrains.compose.resources.StringResource

/** How a [WalletType] is described to the user, e.g. under the wallet's name in the drawer. */
fun WalletType.labelRes(): StringResource = when (this) {
    WalletType.Solo -> Res.string.shell_wallet_type_solo
    WalletType.Shared -> Res.string.shell_wallet_type_shared
}
