package com.example.tugas9.di

import com.example.tugas9.data.local.SettingsManager
import com.example.tugas9.data.local.createSettings
import com.example.tugas9.data.repository.AiRepository
import com.example.tugas9.platform.BatteryInfo
import com.example.tugas9.platform.DeviceInfo
import com.example.tugas9.platform.NetworkMonitor
import com.example.tugas9.ui.screens.chat.ChatViewModel
import com.example.tugas9.ui.screens.favorites.FavoritesViewModel
import com.example.tugas9.ui.screens.notes.NotesViewModel
import com.example.tugas9.ui.screens.settings.SettingsViewModel
import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.koin.dsl.module

// ── 1. MODUL DATA (Database, Network, Settings, Platform) ──────────────
val dataModule = module {
    // Ktor HttpClient
    single {
        HttpClient {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    prettyPrint = true
                    isLenient = true
                })
            }
        }
    }

    // AI Repository
    single { AiRepository(get()) }

    // Settings
    single { createSettings() }
    single { SettingsManager(get()) }

    // Platform Services
    single { DeviceInfo() }
    single { NetworkMonitor() }
    single { BatteryInfo() }
}

// ── 2. MODUL VIEWMODEL (Syarat Tugas 10) ───────────────────────────────
val viewModelModule = module {
    factory { NotesViewModel() }
    factory { ChatViewModel(get()) }
    factory { FavoritesViewModel() }
    factory { SettingsViewModel() }
}

// ── 3. GABUNGKAN SEMUA MODUL ───────────────────────────────────────────
val appModule = listOf(dataModule, viewModelModule)