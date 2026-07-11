package com.shah.cashwise.data.local

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.shah.cashwise.db.CashWiseDatabase

/**
 * iOS driver — NativeSqliteDriver stores the file in the app's databases directory.
 *
 * Foreign keys are enabled through SQLiter's configuration (whose default is `false`)
 * so every connection in its pool enforces the `ON DELETE CASCADE` rules.
 */
actual fun createSqlDriver(): SqlDriver = NativeSqliteDriver(
    schema = CashWiseDatabase.Schema,
    name = DATABASE_FILE_NAME,
    onConfiguration = { configuration ->
        configuration.copy(
            extendedConfig = configuration.extendedConfig.copy(foreignKeyConstraints = true),
        )
    },
)
