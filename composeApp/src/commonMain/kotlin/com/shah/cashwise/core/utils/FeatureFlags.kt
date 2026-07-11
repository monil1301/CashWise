package com.shah.cashwise.core.utils

/**
 * Compile-time feature flags. Sync-related UI and engines must stay gated here
 * (per AGENTS.md) until they are implemented and verified.
 */
object FeatureFlags {
    /**
     * Whether the offline-first sync engine is active. Off until the sync
     * coordinator and data layer land — see [com.shah.cashwise.data.sync.SyncCoordinator].
     */
    const val SYNC_ENABLED: Boolean = false
}
