package com.shah.cashwise.domain.model

import cashwise.composeapp.generated.resources.Res
import cashwise.composeapp.generated.resources.currency_name_aud
import cashwise.composeapp.generated.resources.currency_name_eur
import cashwise.composeapp.generated.resources.currency_name_gbp
import cashwise.composeapp.generated.resources.currency_name_inr
import cashwise.composeapp.generated.resources.currency_name_jpy
import cashwise.composeapp.generated.resources.currency_name_usd
import org.jetbrains.compose.resources.StringResource

/**
 * A currency a wallet can be denominated in. [code] (ISO 4217) and [symbol] are
 * universal, so they are plain strings; the human-readable name is a
 * [StringResource] so it stays localizable.
 */
data class Currency(
    val code: String,
    val symbol: String,
    val displayNameRes: StringResource,
)

/** Currencies offered during setup. The first entry is the default selection. */
val SupportedCurrencies: List<Currency> = listOf(
    Currency(code = "INR", symbol = "₹", displayNameRes = Res.string.currency_name_inr),
    Currency(code = "USD", symbol = "$", displayNameRes = Res.string.currency_name_usd),
    Currency(code = "EUR", symbol = "€", displayNameRes = Res.string.currency_name_eur),
    Currency(code = "GBP", symbol = "£", displayNameRes = Res.string.currency_name_gbp),
    Currency(code = "JPY", symbol = "¥", displayNameRes = Res.string.currency_name_jpy),
    Currency(code = "AUD", symbol = "A$", displayNameRes = Res.string.currency_name_aud),
)

/** The currency selected by default when setup starts. */
val DefaultCurrency: Currency = SupportedCurrencies.first()
