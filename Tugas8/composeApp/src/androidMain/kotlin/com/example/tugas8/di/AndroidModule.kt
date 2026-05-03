package com.example.tugas8.di

import com.example.tugas8.DatabaseDriverFactory
import com.example.tugas8.NoteRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

/**
 * androidModule — Modul khusus Android.
 * Menyediakan Context-dependent dependencies seperti DatabaseDriverFactory.
 */
val androidModule = module {

    // DatabaseDriverFactory butuh Android Context
    single { DatabaseDriverFactory(androidContext()) }

    // NoteRepository butuh DatabaseDriverFactory
    single { NoteRepository(get()) }
}
