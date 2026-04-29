package com.example.tugas7

import android.app.Application
import com.russhwolf.settings.Settings
import com.russhwolf.settings.SharedPreferencesSettings

// Disuntikkan dari MainActivity
lateinit var appContext: Application

actual fun createSettings(): Settings =
    SharedPreferencesSettings.Factory(appContext).create("app_prefs")
