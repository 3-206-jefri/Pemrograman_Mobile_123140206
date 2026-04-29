package com.example.tugas7

import com.russhwolf.settings.NSUserDefaultsSettings
import com.russhwolf.settings.Settings

actual fun createSettings(): Settings = NSUserDefaultsSettings.Factory().create("app_prefs")
