package com.example.tugas8.di

import com.example.tugas8.NoteRepository
import com.example.tugas8.SettingsManager
import com.example.tugas8.createSettings
import com.example.tugas8.platform.BatteryInfo
import com.example.tugas8.platform.DeviceInfo
import com.example.tugas8.platform.NetworkMonitor
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
    single { BatteryInfo() }   // BONUS
}
