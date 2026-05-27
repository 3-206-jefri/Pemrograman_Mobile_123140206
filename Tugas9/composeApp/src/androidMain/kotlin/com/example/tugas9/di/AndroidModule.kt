package com.example.tugas9.di

import com.example.tugas9.DatabaseDriverFactory
import com.example.tugas9.NoteRepository
import org.koin.android.ext.koin.androidContext
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
