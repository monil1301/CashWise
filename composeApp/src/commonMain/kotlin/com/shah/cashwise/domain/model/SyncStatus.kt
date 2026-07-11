package com.shah.cashwise.domain.model

/**
 * How the local database currently stands relative to the backend.
 *
 * The shell renders this in the top bar, and it renders it *asymmetrically* on purpose:
 * [Synced] is the boring, overwhelmingly common case, so it gets only a small green dot on
 * the wallet avatar. Every other state is something the user may need to act on, so it gets
 * a labelled pill. Quiet when all is well, loud when it is not.
 */
enum class SyncStatus {
    /** Local and remote agree. Nothing to say — a dot, no pill. */
    Synced,

    /** A push/pull is in flight. */
    Syncing,

    /** No usable connection. Edits are still saved locally and will sync later. */
    Offline,

    /** The last sync attempt failed and will not retry on its own. */
    Error,
}
