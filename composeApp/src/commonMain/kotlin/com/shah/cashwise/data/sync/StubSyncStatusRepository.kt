package com.shah.cashwise.data.sync

import com.shah.cashwise.core.utils.FeatureFlags
import com.shah.cashwise.domain.model.SyncStatus
import com.shah.cashwise.domain.repo.SyncStatusRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

/**
 * Placeholder status source used while [FeatureFlags.SYNC_ENABLED] is off.
 *
 * It reports [SyncStatus.Synced] — not as a white lie, but because with the sync engine
 * disabled the app is purely local, so there is by definition no un-synced work outstanding.
 * The shell therefore shows the quiet green dot and no pill, which is exactly right for an
 * offline-only build.
 *
 * TODO(sync): replace with a real implementation backed by [SyncCoordinator] — it should
 *  emit [SyncStatus.Syncing] while a push/pull is in flight, [SyncStatus.Offline] when there
 *  is no connection with local edits pending, and [SyncStatus.Error] when an attempt failed.
 *  The shell already renders all four, so only this binding needs to change.
 */
class StubSyncStatusRepository : SyncStatusRepository {
    override val status: Flow<SyncStatus> = flowOf(SyncStatus.Synced)
}
