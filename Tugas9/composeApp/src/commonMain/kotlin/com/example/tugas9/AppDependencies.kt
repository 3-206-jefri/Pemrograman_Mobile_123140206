package com.example.tugas9

import com.example.tugas9.ui.settings.SettingsManager

/**
 * Singleton sederhana untuk menyimpan dependensi global.
 * Diinisialisasi sekali dari MainActivity / MainViewController.
 */
object AppDependencies {
    lateinit var noteRepository: NoteRepository
    lateinit var settingsManager: SettingsManager
}
