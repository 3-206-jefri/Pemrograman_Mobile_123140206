package com.example.tugas9

import android.content.Context
import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.SharedPreferencesSettings
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

// createSettings dipanggil dari AppModule (Koin), context tersedia via androidContext()
// Kita buat helper object supaya bisa inject context
private object SettingsHelper : KoinComponent {
    val context: Context by inject()
}

actual fun createSettings(): ObservableSettings =
    SharedPreferencesSettings.Factory(SettingsHelper.context).create("app_prefs")
