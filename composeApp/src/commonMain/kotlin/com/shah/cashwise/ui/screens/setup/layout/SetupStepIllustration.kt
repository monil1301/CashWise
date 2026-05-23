package com.shah.cashwise.ui.screens.setup.layout

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cashwise.composeapp.generated.resources.Res
import cashwise.composeapp.generated.resources.setup_illustration_placeholder
import com.shah.cashwise.ui.components.ImagePlaceholder
import com.shah.cashwise.ui.screens.setup.illustration.SetupBudgetIllustration
import com.shah.cashwise.ui.screens.setup.illustration.SetupInviteIllustration
import com.shah.cashwise.ui.screens.setup.illustration.SetupLockIllustration
import com.shah.cashwise.ui.screens.setup.illustration.SetupWalletIllustration
import org.jetbrains.compose.resources.stringResource

/**
 * Step-specific artwork for the expanded layout's brand panel. Steps 0, 2, 3,
 * and 4 have built-in composable illustrations; the rest show an
 * [ImagePlaceholder] until real artwork is supplied — swap that for
 * `Image(painterResource(...))` once the asset exists.
 */
@Composable
internal fun SetupStepIllustration(
    currentStep: Int,
    modifier: Modifier = Modifier,
) {
    when (currentStep) {
        0 -> SetupWalletIllustration(modifier = modifier)

        2 -> SetupLockIllustration(modifier = modifier)

        3 -> SetupBudgetIllustration(modifier = modifier)

        4 -> SetupInviteIllustration(modifier = modifier)

        else -> ImagePlaceholder(
            modifier = modifier
                .fillMaxWidth(0.7f)
                .aspectRatio(1f),
            label = stringResource(Res.string.setup_illustration_placeholder),
        )
    }
}
