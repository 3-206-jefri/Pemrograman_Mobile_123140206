package com.example.tugas9.di

import android.net.http.HttpResponseCache.install
import com.example.tugas9.data.local.SettingsManager
import com.example.tugas9.data.local.createSettings
import com.example.tugas9.platform.BatteryInfo
import com.example.tugas9.platform.DeviceInfo
import com.example.tugas9.platform.NetworkMonitor
import io.ktor.client.HttpClient
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


    // ── Settings ──────────────────────────────────────────────────────────────
    single { createSettings() }
    single { SettingsManager(get()) }

    // ── Repository ────────────────────────────────────────────────────────────
    // NoteRepository sudah ada dari Tugas 7, sekarang di-inject via Koin
    // DatabaseDriverFactory di-inject dari androidModule (butuh Context)

    // ── Platform Services ─────────────────────────────────────────────────────
    single { DeviceInfo() }
    single { NetworkMonitor() }
    single { BatteryInfo() }
// Ubah menjadi seperti ini:

}
