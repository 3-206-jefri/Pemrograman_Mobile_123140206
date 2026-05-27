package com.example.tugas9

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.tugas9.data.repository.NoteRepository
import com.example.tugas9.di.AppDependencies
import com.example.tugas9.data.local.SettingsManager
import org.koin.android.ext.android.inject

/**
 * MainActivity — sekarang lebih bersih karena semua dependencies
 * dikelola oleh Koin (tidak perlu init manual AppDependencies lagi).
 */
class MainActivity : ComponentActivity() {

    // Inject via Koin
    private val noteRepository: NoteRepository by inject()
    private val settingsManager: SettingsManager by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // AppDependencies masih diisi agar ViewModel lama tetap bekerja
        // (ViewModel masih pakai AppDependencies karena belum migrasi penuh ke Koin)
        AppDependencies.noteRepository = noteRepository
        AppDependencies.settingsManager = settingsManager

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
