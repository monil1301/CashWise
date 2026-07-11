package com.shah.cashwise.core.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

/**
 * Native has no public `Dispatchers.IO`, so Default is the best option available.
 * Acceptable here: SQLite calls for a single local user are short, and the work is
 * still off the main thread — which is the property that actually matters.
 */
actual val ioDispatcher: CoroutineDispatcher = Dispatchers.Default
