package com.example.tugas7

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inisialisasi AppDependencies saat app pertama kali dibuka
        val isInitialized = try { AppDependencies.noteRepository; true } catch (e: UninitializedPropertyAccessException) { false }
        if (!isInitialized) {
            appContext = application
            val driverFactory = DatabaseDriverFactory(applicationContext)
            AppDependencies.noteRepository = NoteRepository(driverFactory)
            // Menggunakan factory untuk mendapatkan ObservableSettings
            AppDependencies.settingsManager = SettingsManager(createSettings())
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
