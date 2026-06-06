package com.example.tugas9.di

import com.example.tugas9.data.local.DatabaseDriverFactory
import com.example.tugas9.data.repository.NoteRepository
import org.koin.dsl.module

/**
 * androidModule — Modul khusus Android.
 * Menyediakan Context-dependent dependencies seperti DatabaseDriverFactory.
 */
val androidModule = module {

    // DatabaseDriverFactory butuh Android Context
    single { DatabaseDriverFactory() }

    // NoteRepository butuh DatabaseDriverFactory
    single { NoteRepository(get()) }
}
