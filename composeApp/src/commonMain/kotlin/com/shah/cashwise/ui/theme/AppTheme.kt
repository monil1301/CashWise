package com.shah.cashwise.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

// Cashwise palette (Premium Emerald)

@Composable
fun CashWiseTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (useDarkTheme) CashwiseDark else CashwiseLight
    MaterialTheme(
        colorScheme = colorScheme,
        typography = cashwiseTypography(),
        content = content
    )
}

private val CashwiseLight = ColorScheme(
    primary = GreenEmeraldDeep,
    onPrimary = White,
    primaryContainer = MintSpring,
    onPrimaryContainer = PineBlack,
    inversePrimary = EmeraldLight,

    secondary = TealDeep,
    onSecondary = White,
    secondaryContainer = AquaMist,
    onSecondaryContainer = TealBlack,

    tertiary = AmberBrown,
    onTertiary = White,
    tertiaryContainer = Sand,
    onTertiaryContainer = BrownDeep,

    background = Alabaster,
    onBackground = Charcoal,

    surface = White,
    onSurface = Charcoal,
    surfaceVariant = GrayHawkes,
    onSurfaceVariant = GrayOuterSpace,
    surfaceTint = GreenEmeraldDeep,

    inverseSurface = RaisinBlack,
    inverseOnSurface = GrayPorcelain,

    error = RedCopper,
    onError = White,
    errorContainer = RoseWhite,
    onErrorContainer = Oxblood,

    outline = GrayBattleship,
    outlineVariant = GrayGainsboro,

    scrim = Black,

    surfaceBright = White,
    surfaceDim = GrayMercury,
    surfaceContainer = GrayPampas,
    surfaceContainerHigh = GrayPorcelain,
    surfaceContainerHighest = GrayAlto,
    surfaceContainerLow = GrayBianco,
    surfaceContainerLowest = White,

    primaryFixed = MintSpring,
    primaryFixedDim = EmeraldLight,
    onPrimaryFixed = PineBlack,
    onPrimaryFixedVariant = GreenHunter,

    secondaryFixed = AquaMist,
    secondaryFixedDim = Turquoise,
    onSecondaryFixed = TealBlack,
    onSecondaryFixedVariant = TealPine,

    tertiaryFixed = Sand,
    tertiaryFixedDim = Maize,
    onTertiaryFixed = BrownDeep,
    onTertiaryFixedVariant = Bronze,
)

private val CashwiseDark = ColorScheme(
    primary = EmeraldLight,
    onPrimary = GreenBottle,
    primaryContainer = GreenHunter,
    onPrimaryContainer = MintSpring,
    inversePrimary = GreenEmeraldDeep,

    secondary = Turquoise,
    onSecondary = TealBottle,
    secondaryContainer = TealPine,
    onSecondaryContainer = AquaMist,

    tertiary = Maize,
    onTertiary = BrownSepia,
    tertiaryContainer = Bronze,
    onTertiaryContainer = Sand,

    background = BlackPearl,
    onBackground = GrayAthens,

    surface = BlackPearl,
    onSurface = GrayAthens,
    surfaceVariant = GrayOuterSpace,
    onSurfaceVariant = GrayGainsboro,
    surfaceTint = EmeraldLight,

    inverseSurface = GrayAthens,
    inverseOnSurface = RaisinBlack,

    error = PinkLight,
    onError = Maroon,
    errorContainer = RedBrick,
    onErrorContainer = RoseWhite,

    outline = GraySilver,
    outlineVariant = GrayOuterSpace,

    scrim = Black,

    surfaceBright = BlackOlive,
    surfaceDim = BlackNero,
    surfaceContainer = BlackJungle,
    surfaceContainerHigh = BlackOlive,
    surfaceContainerHighest = Gunmetal,
    surfaceContainerLow = BlackCharcoal,
    surfaceContainerLowest = BlackOnyx,

    primaryFixed = MintSpring,
    primaryFixedDim = EmeraldLight,
    onPrimaryFixed = PineBlack,
    onPrimaryFixedVariant = GreenHunter,

    secondaryFixed = AquaMist,
    secondaryFixedDim = Turquoise,
    onSecondaryFixed = TealBlack,
    onSecondaryFixedVariant = TealPine,

    tertiaryFixed = Sand,
    tertiaryFixedDim = Maize,
    onTertiaryFixed = BrownDeep,
    onTertiaryFixedVariant = Bronze,
)
