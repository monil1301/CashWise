package com.shah.cashwise.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

/**
 * The CashWise type scale, per `DESIGN_SYSTEM.md`: a single fixed scale anchored
 * on Display 32 / Title 20 / Body 14 / Label 12 / Caption 11. Not responsive —
 * the same sizes apply at every window width.
 */
fun cashWiseTypography(
    fontFamily: FontFamily = FontFamily.Default,
): Typography {
    fun style(weight: FontWeight, size: Int, lineHeight: Int): TextStyle = TextStyle(
        fontFamily = fontFamily,
        fontWeight = weight,
        fontSize = size.sp,
        lineHeight = lineHeight.sp,
        letterSpacing = 0.sp,
    )

    return Typography(
        // Display — onboarding/screen headlines, hero numbers
        displayLarge = style(FontWeight.Bold, size = 32, lineHeight = 40),
        displayMedium = style(FontWeight.Bold, size = 28, lineHeight = 36),
        displaySmall = style(FontWeight.Bold, size = 24, lineHeight = 32),
        // Headline
        headlineLarge = style(FontWeight.SemiBold, size = 24, lineHeight = 32),
        headlineMedium = style(FontWeight.SemiBold, size = 22, lineHeight = 30),
        headlineSmall = style(FontWeight.SemiBold, size = 20, lineHeight = 28),
        // Title — screen titles, card headers
        titleLarge = style(FontWeight.SemiBold, size = 20, lineHeight = 28),
        titleMedium = style(FontWeight.SemiBold, size = 16, lineHeight = 22),
        titleSmall = style(FontWeight.SemiBold, size = 14, lineHeight = 18),
        // Body — descriptions, list rows
        bodyLarge = style(FontWeight.Normal, size = 16, lineHeight = 22),
        bodyMedium = style(FontWeight.Normal, size = 14, lineHeight = 20),
        // Caption — timestamps, helper text
        bodySmall = style(FontWeight.Normal, size = 11, lineHeight = 16),
        // Label — buttons, chips, tabs
        labelLarge = style(FontWeight.Medium, size = 12, lineHeight = 16),
        labelMedium = style(FontWeight.Medium, size = 12, lineHeight = 16),
        labelSmall = style(FontWeight.Medium, size = 11, lineHeight = 14),
    )
}
