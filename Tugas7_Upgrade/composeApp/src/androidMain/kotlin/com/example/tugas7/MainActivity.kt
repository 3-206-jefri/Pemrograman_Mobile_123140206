package com.example.tugas7

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.russhwolf.settings.SharedPreferencesSettings

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inisialisasi AppDependencies saat app pertama kali dibuka
        if (!AppDependencies::noteRepository.isInitialized) {
            appContext = application
            val driverFactory = DatabaseDriverFactory(applicationContext)
            AppDependencies.noteRepository = NoteRepository(driverFactory)
            val settings = SharedPreferencesSettings.Factory(applicationContext).create("app_prefs")
            AppDependencies.settingsManager = SettingsManager(settings)
        }

        setContent {
            App()
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}
