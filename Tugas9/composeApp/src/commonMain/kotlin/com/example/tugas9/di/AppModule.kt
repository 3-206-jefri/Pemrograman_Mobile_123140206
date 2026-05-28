package com.example.tugas9.di

import com.example.tugas9.data.local.SettingsManager
import com.example.tugas9.data.local.createSettings
import com.example.tugas9.data.repository.AiRepository
import com.example.tugas9.platform.BatteryInfo
import com.example.tugas9.platform.DeviceInfo
import com.example.tugas9.platform.NetworkMonitor
import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.koin.dsl.module

/**
 * Koin Module — mendaftarkan semua dependencies untuk injection.
 * Sesuai materi Pertemuan 8: Dependency Injection dengan Koin.
 *
 * single{}     = Singleton, satu instance sepanjang app hidup
 * factory{}    = Instance baru setiap kali dipanggil
 */
val appModule = module {
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

    // ── AI Repository ──────────────────────────────────────────────────────────
    single { AiRepository(get()) }

    // ── Settings ──────────────────────────────────────────────────────────────
    single { createSettings() }
    single { SettingsManager(get()) }

    // ── Platform Services ─────────────────────────────────────────────────────
    single { DeviceInfo() }
    single { NetworkMonitor() }
    single { BatteryInfo() }
}
