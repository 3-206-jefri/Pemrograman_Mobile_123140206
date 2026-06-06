package com.example.tugas9.di

import com.example.tugas9.data.repository.NoteRepository
import com.example.tugas9.data.local.SettingsManager

/**
 * Singleton sederhana untuk menyimpan dependensi global.
 * Diinisialisasi sekali dari MainActivity / MainViewController.
 */
object AppDependencies {
    lateinit var noteRepository: NoteRepository
    lateinit var settingsManager: SettingsManager
}