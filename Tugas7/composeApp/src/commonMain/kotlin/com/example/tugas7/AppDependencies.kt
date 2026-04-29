package com.example.tugas7

/**
 * Singleton sederhana untuk menyimpan dependensi global.
 * Diinisialisasi sekali dari MainActivity / MainViewController.
 */
object AppDependencies {
    lateinit var noteRepository: NoteRepository
    lateinit var settingsManager: SettingsManager
}
