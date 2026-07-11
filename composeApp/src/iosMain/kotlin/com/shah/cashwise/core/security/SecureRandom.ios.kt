package com.shah.cashwise.core.security

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.usePinned
import platform.Security.SecRandomCopyBytes
import platform.Security.kSecRandomDefault

/** Backed by the system CSPRNG (`SecRandomCopyBytes`), the same source the Keychain uses. */
@OptIn(ExperimentalForeignApi::class)
actual fun secureRandomBytes(size: Int): ByteArray {
    val bytes = ByteArray(size)
    bytes.usePinned { pinned ->
        val status = SecRandomCopyBytes(kSecRandomDefault, size.toULong(), pinned.addressOf(0))
        check(status == 0) { "SecRandomCopyBytes failed with status $status" }
    }
    return bytes
}
