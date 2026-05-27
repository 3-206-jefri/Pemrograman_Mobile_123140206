package com.example.tugas9.platform

import kotlinx.coroutines.flow.Flow

/**
 * expect class NetworkMonitor
 * Deklarasi di commonMain — implementasi ada di setiap platform (actual).
 * Sesuai materi Pertemuan 8: expect/actual pattern.
 */
expect class NetworkMonitor() {
    fun isConnected(): Boolean
    fun observeConnectivity(): Flow<Boolean>
}
