package com.shah.cashwise.ui.screens.setup.model

import androidx.compose.runtime.saveable.listSaver

/**
 * Locally-issued invite credentials surfaced by the "Invite a member" sheet —
 * the formatted human-readable [code] (e.g. `CW-7K2P-9XQ`), the [link] that
 * resolves to the same invite, and the [expiresInDays] caption.
 *
 * This is a placeholder for the eventual signed-envelope payload — see
 * [generateInviteCredentials] for the TODO that tracks the real issuance.
 */
data class InviteCredentials(
    val code: String,
    val link: String,
    val expiresInDays: Int,
)

/** `Saver` used to persist [InviteCredentials] across configuration changes. */
internal val InviteCredentialsSaver = listSaver<InviteCredentials, Any>(
    save = { listOf(it.code, it.link, it.expiresInDays) },
    restore = {
        InviteCredentials(
            code = it[0] as String,
            link = it[1] as String,
            expiresInDays = it[2] as Int,
        )
    },
)
