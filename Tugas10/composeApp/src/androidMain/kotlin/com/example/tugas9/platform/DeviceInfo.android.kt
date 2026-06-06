package com.example.tugas9.platform

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.math.pow
import kotlin.math.sqrt

/**
 * actual class DeviceInfo — Android implementation.
 * Menggunakan Build.MODEL, Build.VERSION, dan DisplayMetrics.
 */
actual class DeviceInfo : KoinComponent {

    private val context: Context by inject()

    actual fun getDeviceName(): String {
        val manufacturer = Build.MANUFACTURER.replaceFirstChar { it.uppercase() }
        val model = Build.MODEL
        return if (model.startsWith(manufacturer, ignoreCase = true)) model
        else "$manufacturer $model"
    }

    actual fun getOsVersion(): String =
        "Android ${Build.VERSION.RELEASE} (API ${Build.VERSION.SDK_INT})"

    actual fun getAppVersion(): String {
        return try {
            val info = context.packageManager.getPackageInfo(context.packageName, 0)
            info.versionName ?: "1.0.0"
        } catch (e: PackageManager.NameNotFoundException) {
            "1.0.0"
        }
    }

    actual fun isTablet(): Boolean {
        val metrics = context.resources.displayMetrics
        val widthInch  = metrics.widthPixels  / metrics.xdpi
        val heightInch = metrics.heightPixels / metrics.ydpi
        val diagonal   = sqrt(widthInch.pow(2) + heightInch.pow(2))
        return diagonal >= 7.0
    }
}
