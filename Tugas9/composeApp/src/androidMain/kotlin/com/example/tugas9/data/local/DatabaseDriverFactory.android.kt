package com.example.tugas9.data.local

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.example.tugas9.db.NotesDatabase
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


actual class DatabaseDriverFactory : KoinComponent {
    private val context: Context by inject()
    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(NotesDatabase.Schema, context, "notes.db")
    }
}
