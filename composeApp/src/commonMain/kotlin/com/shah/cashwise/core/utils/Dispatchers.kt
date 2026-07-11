package com.shah.cashwise.core.utils

import kotlinx.coroutines.CoroutineDispatcher

/**
 * Dispatcher for blocking disk work (SQLite). `Dispatchers.IO` is not available
 * in common code — it is internal on Native — so each platform supplies its own.
 * Never run these calls on `Dispatchers.Default`: they block, and Default's pool
 * is sized for CPU work.
 */
expect val ioDispatcher: CoroutineDispatcher
