package com.shah.cashwise.data.local

import app.cash.sqldelight.db.SqlDriver

/** File name for the on-device SQLite database, one per platform app directory. */
internal const val DATABASE_FILE_NAME = "cashwise.db"

/**
 * Builds the platform SQL driver. Mirrors [createDataStore]: each platform resolves
 * its own app directory (app files dir on Android, documents on iOS, user home on
 * Desktop) — see each platform's `platformModule`.
 *
 * Each actual must enable foreign keys **through its driver's configuration**, not
 * by executing `PRAGMA foreign_keys=ON` once: the pragma is per-connection, and every
 * driver here hands out more than one connection over its life, so a one-shot pragma
 * silently leaves the `ON DELETE CASCADE` rules unenforced.
 */
expect fun createSqlDriver(): SqlDriver
