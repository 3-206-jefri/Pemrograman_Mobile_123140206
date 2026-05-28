package com.example.tugas9.data.local

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.example.tugas9.db.NotesDatabase
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import android.app.Application

actual class DatabaseDriverFactory : KoinComponent {
    private val application: Application by inject()
    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(NotesDatabase.Schema, application, "notes.db")
    }
}
