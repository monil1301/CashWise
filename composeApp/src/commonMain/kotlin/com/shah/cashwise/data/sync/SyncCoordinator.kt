package com.shah.cashwise.data.sync

/**
 * Seam for the future offline-first sync engine. When a user is signed in and
 * [com.shah.cashwise.core.utils.FeatureFlags.SYNC_ENABLED] is true, an
 * implementation will push local changes and pull remote ones against the
 * Supabase Postgres backend.
 *
 * TODO(sync): implement push/pull + conflict resolution. OUT OF SCOPE for the
 *  auth + session foundation — no implementation is registered in DI yet.
 */
interface SyncCoordinator {
    /** Begin observing local changes and reconciling with the backend. */
    suspend fun start()

    /** Stop syncing (e.g. on sign-out). */
    suspend fun stop()
}
