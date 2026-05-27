package com.example.tugas9.di

import com.example.tugas9.ui.settings.SettingsManager
import com.example.tugas9.ui.settings.createSettings
import com.example.tugas9.platform.BatteryInfo
import com.example.tugas9.platform.DeviceInfo
import com.example.tugas9.platform.NetworkMonitor
import org.koin.dsl.module

/**
 * Koin Module — mendaftarkan semua dependencies untuk injection.
 * Sesuai materi Pertemuan 8: Dependency Injection dengan Koin.
 *
 * single{}     = Singleton, satu instance sepanjang app hidup
 * factory{}    = Instance baru setiap kali dipanggil
 */
val appModule = module {

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
