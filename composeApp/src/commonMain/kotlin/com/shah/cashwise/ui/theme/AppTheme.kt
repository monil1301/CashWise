package com.shah.cashwise.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color

object CashWiseThemeTokens {
    val supportColors: CashWiseSupportColors
        @Composable get() = LocalCashWiseSupportColors.current
}

@Composable
fun CashWiseTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (useDarkTheme) CashwiseDark else CashwiseLight
    val supportColors = if (useDarkTheme) {
        CashWiseSupportColors(
            warning = AmberDark,
            onWarning = SurfaceDark,
            warningContainer = AmberContainerDark,
            onWarningContainer = OnAmberContainerDark,
        )
    } else {
        CashWiseSupportColors(
            warning = Amber,
            onWarning = OnSurfaceCharcoal,
            warningContainer = AmberContainer,
            onWarningContainer = OnAmberContainer,
        )
    }

    CompositionLocalProvider(LocalCashWiseSupportColors provides supportColors) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = cashWiseTypography(),
            shapes = CashWiseShapes,
            content = content
        )
    }
}

private val CashwiseLight = ColorScheme(
    primary = DeepTeal,
    onPrimary = White,
    primaryContainer = LightTeal,
    onPrimaryContainer = OnSurfaceCharcoal,
    inversePrimary = DeepTealDark,

    secondary = Indigo,
    onSecondary = White,
    secondaryContainer = LightIndigo,
    onSecondaryContainer = OnSurfaceCharcoal,

    tertiary = Amber,
    onTertiary = OnSurfaceCharcoal,
    tertiaryContainer = Color(0xFFFFF3C4),
    onTertiaryContainer = OnSurfaceCharcoal,

    background = SurfaceOffWhite,
    onBackground = OnSurfaceCharcoal,

    surface = SurfaceWhite,
    onSurface = OnSurfaceCharcoal,
    surfaceVariant = SurfaceOffWhite,
    onSurfaceVariant = OnSurfaceSlate,
    surfaceTint = DeepTeal,

    inverseSurface = OnSurfaceCharcoal,
    inverseOnSurface = SurfaceOffWhite,

    error = SoftRed,
    onError = White,
    errorContainer = Color(0xFFFEE2E2),
    onErrorContainer = OnSurfaceCharcoal,

    outline = OutlineLight,
    outlineVariant = OutlineLight,

    scrim = Black,

    surfaceBright = SurfaceWhite,
    surfaceDim = Color(0xFFF1F5F9),
    surfaceContainer = Color(0xFFF8FAFC),
    surfaceContainerHigh = Color(0xFFF1F5F9),
    surfaceContainerHighest = Color(0xFFEFF4F8),
    surfaceContainerLow = SurfaceWhite,
    surfaceContainerLowest = SurfaceWhite,

    primaryFixed = LightTeal,
    primaryFixedDim = DeepTeal,
    onPrimaryFixed = OnSurfaceCharcoal,
    onPrimaryFixedVariant = DeepTeal,

    secondaryFixed = LightIndigo,
    secondaryFixedDim = Indigo,
    onSecondaryFixed = OnSurfaceCharcoal,
    onSecondaryFixedVariant = Indigo,

    tertiaryFixed = Color(0xFFFFF3C4),
    tertiaryFixedDim = Amber,
    onTertiaryFixed = OnSurfaceCharcoal,
    onTertiaryFixedVariant = Amber,
)

private val CashwiseDark = ColorScheme(
    primary = DeepTealDark,
    onPrimary = SurfaceDark,
    primaryContainer = DeepTealContainerDark,
    onPrimaryContainer = LightTeal,
    inversePrimary = DeepTeal,

    secondary = IndigoDark,
    onSecondary = SurfaceDark,
    secondaryContainer = IndigoContainerDark,
    onSecondaryContainer = LightIndigo,

    tertiary = AmberDark,
    onTertiary = SurfaceDark,
    tertiaryContainer = Color(0xFF78350F),
    onTertiaryContainer = Color(0xFFFDE68A),

    background = SurfaceDark,
    onBackground = OnSurfaceDark,

    surface = SurfaceDark,
    onSurface = OnSurfaceDark,
    surfaceVariant = SurfaceVariantDark,
    onSurfaceVariant = OnSurfaceVariantDark,
    surfaceTint = DeepTealDark,

    inverseSurface = OnSurfaceDark,
    inverseOnSurface = SurfaceDark,

    error = SoftRedDark,
    onError = SurfaceDark,
    errorContainer = SoftRedContainerDark,
    onErrorContainer = Color(0xFFFEE2E2),

    outline = OutlineDark,
    outlineVariant = OutlineDark,

    scrim = Black,

    surfaceBright = Color(0xFF1E293B),
    surfaceDim = Color(0xFF0B1220),
    surfaceContainer = Color(0xFF111C31),
    surfaceContainerHigh = Color(0xFF17243A),
    surfaceContainerHighest = SurfaceVariantDark,
    surfaceContainerLow = Color(0xFF0F1A2E),
    surfaceContainerLowest = Color(0xFF091120),

    primaryFixed = LightTeal,
    primaryFixedDim = DeepTealDark,
    onPrimaryFixed = SurfaceDark,
    onPrimaryFixedVariant = DeepTealContainerDark,

    secondaryFixed = LightIndigo,
    secondaryFixedDim = IndigoDark,
    onSecondaryFixed = SurfaceDark,
    onSecondaryFixedVariant = IndigoContainerDark,

    tertiaryFixed = Color(0xFFFDE68A),
    tertiaryFixedDim = AmberDark,
    onTertiaryFixed = SurfaceDark,
    onTertiaryFixedVariant = Color(0xFF78350F),
)
