package com.example.tugas8

import android.app.Application
import com.example.tugas8.di.androidModule
import com.example.tugas8.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

/**
 * MyApplication — entry point Android app.
 * Inisialisasi Koin DI di sini agar Context tersedia untuk semua dependencies.
 * Sesuai materi Pertemuan 8: Inisialisasi Koin.
 */
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            // Android logger (tampil di Logcat)
            androidLogger(Level.DEBUG)
            // Sediakan Application Context untuk dependencies yang butuh Context
            androidContext(this@MyApplication)
            // Daftarkan semua modul
            modules(
                appModule,
                androidModule
            )
        }
    }
}
