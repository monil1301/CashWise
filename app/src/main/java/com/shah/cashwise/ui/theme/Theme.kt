package com.shah.cashwise.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

/**
 * Created by Monil on 29/03/25.
 */

private val LightColorScheme = lightColorScheme(
    primary = GreenFruitSalad,
    onPrimary = White,
    primaryContainer = GreenFringyFlower,
    onPrimaryContainer = GreenParsley,
    inversePrimary = GreenDeYork,

    secondary = BlueDodger,
    onSecondary = White,
    secondaryContainer = BlueTropical,
    onSecondaryContainer = BlueCobalt,

    tertiary = YellowAmber,
    onTertiary = Black,
    tertiaryContainer = YellowSaharaSand,
    onTertiaryContainer = BrownWoody,

    background = WhiteAlabaster,
    onBackground = BlackMineShaft,
    surface = White,
    onSurface = BlackMineShaft,
    surfaceVariant = GrayAlto,
    onSurfaceVariant = GrayTundora,
    surfaceTint = GreenFruitSalad,

    inverseSurface = GrayCharade,
    inverseOnSurface = WhiteAlabaster,

    error = RedTorch,
    onError = White,
    errorContainer = RedPale,
    onErrorContainer = RedCrimson,

    outline = GraySilver,
    outlineVariant = GrayAlto,
    scrim = BlackTranslucent
)

private val DarkColorScheme = darkColorScheme(
    primary = GreenDeYork,
    onPrimary = Black,
    primaryContainer = GreenSalem,
    onPrimaryContainer = GreenFringyFlower,
    inversePrimary = GreenFruitSalad,

    secondary = BlueMalibu,
    onSecondary = Black,
    secondaryContainer = BlueYale,
    onSecondaryContainer = BlueTropical,

    tertiary = YellowMustard,
    onTertiary = Black,
    tertiaryContainer = BrownCedar,
    onTertiaryContainer = YellowCream,

    background = GrayCod,
    onBackground = GrayAlto,
    surface = GrayShark,
    onSurface = GrayAlto,
    surfaceVariant = GrayCharade,
    onSurfaceVariant = GrayBlueSmoke,
    surfaceTint = GreenDeYork,

    inverseSurface = GrayAlto,
    inverseOnSurface = GrayShark,

    error = RedRose,
    onError = Black,
    errorContainer = RedCrimson,
    onErrorContainer = RedPale,

    outline = GrayDove,
    outlineVariant = GrayBoulder,
    scrim = BlackTranslucent
)

@Composable
fun CashWiseTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
//        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
//            val context = LocalContext.current
//            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
//        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}