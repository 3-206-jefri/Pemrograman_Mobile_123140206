package com.example.tugas9

import android.app.Application
import com.example.tugas9.di.androidModule
import com.example.tugas9.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        // ✨ PERBAIKAN: Cek apakah Koin sudah jalan (mencegah error di Robolectric)
        if (GlobalContext.getOrNull() == null) {
            startKoin {
                androidLogger(Level.DEBUG)
                androidContext(this@MyApplication)

                // ✨ PERBAIKAN: Menggabungkan List dan Module Tunggal dengan '+'
                modules(appModule + androidModule)
            }
        }
    }
}