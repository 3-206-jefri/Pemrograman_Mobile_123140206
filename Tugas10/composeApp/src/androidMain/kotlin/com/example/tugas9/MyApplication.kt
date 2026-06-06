package com.example.tugas9

import android.app.Application
import com.example.tugas9.di.androidModule
import com.example.tugas9.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@MyApplication)

            // ✨ PERBAIKAN DI SINI: Menggabungkan List dan Module Tunggal dengan '+'
            modules(appModule + androidModule)
        }
    }
}