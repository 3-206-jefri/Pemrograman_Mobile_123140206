package com.example.tugas8.platform

/**
 * expect class BatteryInfo
 * BONUS +10%: Implementasi battery status via expect/actual.
 */
expect class BatteryInfo() {
    fun getBatteryLevel(): Int   // 0–100
    fun isCharging(): Boolean
}
