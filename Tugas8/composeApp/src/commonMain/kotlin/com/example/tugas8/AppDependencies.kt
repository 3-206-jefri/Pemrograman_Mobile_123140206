package com.example.tugas8

/**
 * Singleton sederhana untuk menyimpan dependensi global.
 * Diinisialisasi sekali dari MainActivity / MainViewController.
 */
object AppDependencies {
    lateinit var noteRepository: NoteRepository
    lateinit var settingsManager: SettingsManager
}
