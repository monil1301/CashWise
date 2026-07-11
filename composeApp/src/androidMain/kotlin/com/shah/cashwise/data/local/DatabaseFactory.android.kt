package com.shah.cashwise.data.local

import androidx.sqlite.db.SupportSQLiteDatabase
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.shah.cashwise.CashWiseApplication
import com.shah.cashwise.db.CashWiseDatabase

/**
 * Android driver — resolves the app files dir via the Application, as the DataStore does.
 *
 * Foreign keys are turned on in `onConfigure` rather than by a one-shot pragma: the
 * framework pools connections (WAL is on by default from API 28), and each connection
 * takes its FK mode from the database configuration. Setting it here is what makes it
 * stick across every pooled connection.
 */
actual fun createSqlDriver(): SqlDriver = AndroidSqliteDriver(
    schema = CashWiseDatabase.Schema,
    context = CashWiseApplication.appContext,
    name = DATABASE_FILE_NAME,
    callback = object : AndroidSqliteDriver.Callback(CashWiseDatabase.Schema) {
        override fun onConfigure(db: SupportSQLiteDatabase) {
            super.onConfigure(db)
            db.setForeignKeyConstraintsEnabled(true)
        }
    },
)
