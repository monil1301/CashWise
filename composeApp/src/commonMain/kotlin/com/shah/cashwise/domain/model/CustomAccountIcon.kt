package com.shah.cashwise.domain.model

/**
 * Glyph chosen for a custom account.
 *
 * A domain enum, not a UI one: the selected value is persisted (by [name]) on the
 * account row, so renaming a constant silently invalidates stored data. Keeping it in
 * `domain/model` makes that storage contract explicit — the Compose glyph it maps to
 * lives in `ui/`, where it belongs.
 */
enum class CustomAccountIcon {
    Cash,
    Bank,
    Card,
    Wallet,
    Savings,
    Pocket,
}
