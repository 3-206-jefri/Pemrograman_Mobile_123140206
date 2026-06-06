package com.example.tugas9.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CloudOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tugas9.platform.NetworkMonitor
import org.koin.compose.koinInject

/**
 * NetworkStatusIndicator — tampil otomatis saat tidak ada koneksi internet.
 * Menggunakan koinInject() untuk mendapat NetworkMonitor dari Koin.
 * AnimatedVisibility untuk animasi muncul/hilang.
 */
@Composable
fun NetworkStatusIndicator() {
    val networkMonitor: NetworkMonitor = koinInject()

    val isConnected by networkMonitor
        .observeConnectivity()
        .collectAsState(initial = true)

    AnimatedVisibility(
        visible = !isConnected,
        enter = expandVertically(),
        exit  = shrinkVertically()
    ) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color    = MaterialTheme.colorScheme.error
        ) {
            Row(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector        = Icons.Default.CloudOff,
                    contentDescription = "Offline",
                    tint               = MaterialTheme.colorScheme.onError
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text  = "Tidak ada koneksi internet",
                    color = MaterialTheme.colorScheme.onError,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}
