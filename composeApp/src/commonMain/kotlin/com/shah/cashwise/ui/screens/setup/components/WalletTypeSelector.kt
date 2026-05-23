package com.shah.cashwise.ui.screens.setup.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import cashwise.composeapp.generated.resources.Res
import cashwise.composeapp.generated.resources.setup_wallet_type_shared
import cashwise.composeapp.generated.resources.setup_wallet_type_shared_hint
import cashwise.composeapp.generated.resources.setup_wallet_type_solo
import cashwise.composeapp.generated.resources.setup_wallet_type_solo_hint
import com.shah.cashwise.domain.model.WalletType
import org.jetbrains.compose.resources.stringResource

/**
 * Solo / Shared pill segmented control with a contextual hint below it.
 * The selected half is filled with the primary colour.
 */
@Composable
internal fun WalletTypeSelector(
    selectedType: WalletType,
    onTypeSelected: (WalletType) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(MaterialTheme.shapes.large)
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .padding(4.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            WalletTypeOption(
                label = stringResource(Res.string.setup_wallet_type_solo),
                icon = Icons.Filled.Person,
                selected = selectedType == WalletType.Solo,
                onClick = { onTypeSelected(WalletType.Solo) },
                modifier = Modifier.weight(1f),
            )
            WalletTypeOption(
                label = stringResource(Res.string.setup_wallet_type_shared),
                icon = Icons.Filled.Group,
                selected = selectedType == WalletType.Shared,
                onClick = { onTypeSelected(WalletType.Shared) },
                modifier = Modifier.weight(1f),
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        val hint = when (selectedType) {
            WalletType.Solo -> Res.string.setup_wallet_type_solo_hint
            WalletType.Shared -> Res.string.setup_wallet_type_shared_hint
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Outlined.Info,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(16.dp),
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = stringResource(hint),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}
