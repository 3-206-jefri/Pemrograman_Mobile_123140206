package com.example.tugas7

import androidx.compose.ui.window.ComposeUIViewController

fun MainViewController() = ComposeUIViewController {
    // Inisialisasi AppDependencies untuk iOS
    if (!AppDependencies::noteRepository.isInitialized) {
        val driverFactory = DatabaseDriverFactory()
        AppDependencies.noteRepository = NoteRepository(driverFactory)
        AppDependencies.settingsManager = SettingsManager(createSettings())
    }
    App()
}
