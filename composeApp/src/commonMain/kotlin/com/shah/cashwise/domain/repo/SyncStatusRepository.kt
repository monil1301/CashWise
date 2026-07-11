package com.shah.cashwise.domain.repo

import com.shah.cashwise.domain.model.SyncStatus
import kotlinx.coroutines.flow.Flow

/**
 * Reports the current [SyncStatus] to the UI.
 *
 * Deliberately read-only and separate from [com.shah.cashwise.data.sync.SyncCoordinator]:
 * the shell needs to *observe* sync, not drive it, and giving the top bar a handle that
 * could start or stop the engine would be an invitation to misuse.
 */
interface SyncStatusRepository {

    /** The current status, re-emitting on every change. */
    val status: Flow<SyncStatus>
}
