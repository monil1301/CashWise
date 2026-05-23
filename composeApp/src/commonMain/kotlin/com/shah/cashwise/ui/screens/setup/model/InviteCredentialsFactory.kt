package com.shah.cashwise.ui.screens.setup.model

import kotlin.random.Random

private const val INVITE_ID_LENGTH = 7
private const val INVITE_CODE_PREFIX = "CW"
private const val INVITE_LINK_HOST = "cashwise.app/join"
private const val INVITE_EXPIRY_DAYS = 7

// Crockford base32 — unambiguous (no I/L/O/U).
private const val INVITE_ALPHABET = "0123456789ABCDEFGHJKMNPQRSTVWXYZ"

/**
 * Generate placeholder invite credentials for the "Invite a member" sheet.
 * Produces a 7-character random id, formatted as `CW-XXXX-XXX` for the code
 * display and as `cashwise.app/join/XXXXXXX` for the link.
 *
 * TODO(invite-payload): replace with a signed envelope (wallet id, owner key,
 * role, expiry, signature) once the Wallet model and invite issuance exist.
 */
internal fun generateInviteCredentials(
    random: Random = Random.Default,
): InviteCredentials {
    val id = buildString(INVITE_ID_LENGTH) {
        repeat(INVITE_ID_LENGTH) {
            append(INVITE_ALPHABET[random.nextInt(INVITE_ALPHABET.length)])
        }
    }
    val code = "$INVITE_CODE_PREFIX-${id.substring(0, 4)}-${id.substring(4)}"
    val link = "$INVITE_LINK_HOST/$id"
    return InviteCredentials(code = code, link = link, expiresInDays = INVITE_EXPIRY_DAYS)
}
