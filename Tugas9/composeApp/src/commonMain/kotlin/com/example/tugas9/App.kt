package com.example.tugas9

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.*
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tugas9.ui.MainScreen
import com.example.tugas9.ui.settings.SettingsViewModel

@Composable
fun App() {
    val settingsVM: SettingsViewModel = viewModel { SettingsViewModel() }
    val theme by settingsVM.theme.collectAsStateWithLifecycle()

    val colorScheme = when (theme) {
        "dark"  -> darkColorScheme()
        "light" -> lightColorScheme()
        else    -> MaterialTheme.colorScheme // system default
    }

    MaterialTheme(colorScheme = colorScheme) {
        MainScreen()
    }
}
