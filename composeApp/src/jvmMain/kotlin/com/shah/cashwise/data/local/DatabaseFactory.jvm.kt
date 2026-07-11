package com.shah.cashwise.data.local

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.shah.cashwise.db.CashWiseDatabase
import java.io.File
import java.util.Properties

/**
 * Desktop driver, backed by a file under `~/.cashwise` (the app dir the DataStore uses).
 *
 * Passing `schema` lets the driver create/migrate off `PRAGMA user_version`. Doing it by
 * hand off `file.exists()` was doubly wrong: a process killed midway through the DDL
 * leaves a file that exists with no tables (bricking every later launch), and a manual
 * `Schema.create` never stamps `user_version`, so migrations could never run.
 *
 * `foreign_keys` is set as a connection property — the JDBC driver opens a connection per
 * statement, so a one-shot pragma would be a guaranteed no-op here.
 */
actual fun createSqlDriver(): SqlDriver {
    val appDir = File(System.getProperty("user.home"), ".cashwise").apply { mkdirs() }
    val dbFile = File(appDir, DATABASE_FILE_NAME)
    return JdbcSqliteDriver(
        url = "jdbc:sqlite:${dbFile.absolutePath}",
        properties = Properties().apply { put("foreign_keys", "true") },
        schema = CashWiseDatabase.Schema,
    )
}
