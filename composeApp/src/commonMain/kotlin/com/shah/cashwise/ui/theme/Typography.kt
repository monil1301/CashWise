package com.shah.cashwise.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

/**
 * Width-driven typography tiers.
 *
 * [Compact] backs the stacked phone / tablet-portrait layouts; [Expanded] backs
 * the wide two-pane layout. The tier is chosen once in `CashWiseTheme` from the
 * current window width — the same signal that drives the layout — so it tracks
 * the layout, not the physical device (a tablet in portrait uses [Compact]).
 */
enum class TypeScale { Compact, Expanded }

/**
 * Builds the CashWise type scale for the given [scale].
 *
 * [Compact] values are unchanged from the mobile spec. Each style keeps its
 * line-height ratio across tiers, so [Expanded] sizes are proportionally leaded.
 */
fun cashWiseTypography(
    scale: TypeScale,
    fontFamily: FontFamily = FontFamily.Default,
): Typography {
    fun style(
        weight: FontWeight,
        compactSize: Int,
        compactLineHeight: Int,
        expandedSize: Int,
    ): TextStyle {
        val size = if (scale == TypeScale.Expanded) expandedSize else compactSize
        val lineHeightRatio = compactLineHeight.toFloat() / compactSize
        return TextStyle(
            fontFamily = fontFamily,
            fontWeight = weight,
            fontSize = size.sp,
            lineHeight = (size * lineHeightRatio).sp,
            letterSpacing = 0.sp,
        )
    }

    return Typography(
        // Display — onboarding headlines, hero numbers
        displayLarge = style(FontWeight.Bold, compactSize = 32, compactLineHeight = 40, expandedSize = 44),
        displayMedium = style(FontWeight.Bold, compactSize = 28, compactLineHeight = 36, expandedSize = 38),
        displaySmall = style(FontWeight.Bold, compactSize = 24, compactLineHeight = 32, expandedSize = 32),
        // Headline
        headlineLarge = style(FontWeight.SemiBold, compactSize = 24, compactLineHeight = 32, expandedSize = 30),
        headlineMedium = style(FontWeight.SemiBold, compactSize = 22, compactLineHeight = 30, expandedSize = 28),
        headlineSmall = style(FontWeight.SemiBold, compactSize = 20, compactLineHeight = 28, expandedSize = 26),
        // Title — screen titles, card headers
        titleLarge = style(FontWeight.SemiBold, compactSize = 20, compactLineHeight = 28, expandedSize = 24),
        titleMedium = style(FontWeight.SemiBold, compactSize = 16, compactLineHeight = 22, expandedSize = 20),
        titleSmall = style(FontWeight.SemiBold, compactSize = 14, compactLineHeight = 18, expandedSize = 18),
        // Body — descriptions, list rows
        bodyLarge = style(FontWeight.Normal, compactSize = 16, compactLineHeight = 22, expandedSize = 18),
        bodyMedium = style(FontWeight.Normal, compactSize = 14, compactLineHeight = 20, expandedSize = 16),
        // Caption — timestamps, helper text
        bodySmall = style(FontWeight.Normal, compactSize = 11, compactLineHeight = 16, expandedSize = 12),
        // Label — buttons, chips, tabs
        labelLarge = style(FontWeight.Medium, compactSize = 12, compactLineHeight = 16, expandedSize = 14),
        labelMedium = style(FontWeight.Medium, compactSize = 12, compactLineHeight = 16, expandedSize = 14),
        labelSmall = style(FontWeight.Medium, compactSize = 11, compactLineHeight = 14, expandedSize = 12),
    )
}
