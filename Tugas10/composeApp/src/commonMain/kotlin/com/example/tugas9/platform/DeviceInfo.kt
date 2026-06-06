package com.example.tugas9.platform

/**
 * expect class DeviceInfo
 * Deklarasi di commonMain — implementasi ada di setiap platform (actual).
 * Sesuai materi Pertemuan 8: expect/actual pattern.
 */
expect class DeviceInfo() {
    fun getDeviceName(): String
    fun getOsVersion(): String
    fun getAppVersion(): String
    fun isTablet(): Boolean
}
