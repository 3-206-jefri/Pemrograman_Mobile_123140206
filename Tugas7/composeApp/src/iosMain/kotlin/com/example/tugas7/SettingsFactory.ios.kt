package com.example.tugas7

import com.russhwolf.settings.NSUserDefaultsSettings
import com.russhwolf.settings.ObservableSettings

actual fun createSettings(): ObservableSettings = NSUserDefaultsSettings.Factory().create("app_prefs")
