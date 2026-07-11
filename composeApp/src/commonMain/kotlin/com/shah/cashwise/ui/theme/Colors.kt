package com.shah.cashwise.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

val White = Color(0xFFFFFFFF)
val Black = Color(0xFF000000)

val DeepTeal = Color(0xFF0D9488)
val LightTeal = Color(0xFFCCFBF1)
val DeepTealDark = Color(0xFF5EEAD4)
val DeepTealContainerDark = Color(0xFF134E4A)

val Indigo = Color(0xFF6366F1)
val LightIndigo = Color(0xFFE0E7FF)
val IndigoDark = Color(0xFFA5B4FC)
val IndigoContainerDark = Color(0xFF312E81)

val SoftRed = Color(0xFFEF4444)
val SoftRedDark = Color(0xFFFCA5A5)
val SoftRedContainerDark = Color(0xFF7F1D1D)

val Amber = Color(0xFFF59E0B)
val AmberDark = Color(0xFFFCD34D)
val AmberContainer = Color(0xFFFEF3C7)
val OnAmberContainer = Color(0xFF92400E)
val AmberContainerDark = Color(0xFF78350F)
val OnAmberContainerDark = Color(0xFFFDE68A)

val SurfaceWhite = Color(0xFFFFFFFF)
val SurfaceOffWhite = Color(0xFFF8FAFC)
val OnSurfaceCharcoal = Color(0xFF1E293B)
val OnSurfaceSlate = Color(0xFF64748B)
val OutlineLight = Color(0xFFE2E8F0)

val SurfaceDark = Color(0xFF0F172A)
val SurfaceVariantDark = Color(0xFF1E293B)
val OnSurfaceDark = Color(0xFFF8FAFC)
val OnSurfaceVariantDark = Color(0xFFCBD5E1)
val OutlineDark = Color(0xFF334155)

/**
 * Colours Material's scheme has no slot for.
 *
 * [warning]/[onWarning] are a solid fill and its content colour; [warningContainer] and
 * [onWarningContainer] are the muted pair, mirroring Material's own container convention.
 * Both pairs exist because they are not interchangeable: [onWarning] is near-black, which is
 * legible on solid amber and illegible on a tinted one.
 */
@Immutable
data class CashWiseSupportColors(
    val warning: Color,
    val onWarning: Color,
    val warningContainer: Color,
    val onWarningContainer: Color,
)

internal val LocalCashWiseSupportColors = staticCompositionLocalOf {
    CashWiseSupportColors(
        warning = Amber,
        onWarning = OnSurfaceCharcoal,
        warningContainer = AmberContainer,
        onWarningContainer = OnAmberContainer,
    )
}
