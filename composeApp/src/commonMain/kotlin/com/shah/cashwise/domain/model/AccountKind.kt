package com.shah.cashwise.domain.model

/** The kind of money account offered (or added) during setup. */
enum class AccountKind {
    Cash,
    Upi,
    Bank,
    Card,
    Custom,
}
