package com.example.tugas3

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun App() {
    MaterialTheme {
        Column(
            modifier = Modifier.fillMaxSize().background(Color(0xFFF5F5F5)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            // 1. Header
            ProfileHeader(
                name = "Jefri Wahyu Fernando Sembiring",
                bio = "Mahasiswa informatika Institut Teknologi || Sumatera"
            )

            // 2. Info List di dalam Card
            ProfileCard {
                InfoItem(Icons.Default.Email, "Email", "jefri.123140206@student.itera.ac.id")
                HorizontalDivider(thickness = 0.5.dp, color = Color.LightGray) // Menggunakan HorizontalDivider

                InfoItem(Icons.Default.Phone, "Telepon", "+62 819 1993 1914")
                HorizontalDivider(thickness = 0.5.dp, color = Color.LightGray)

                InfoItem(Icons.Default.LocationOn, "Lokasi", "Lampung Selatan, Indonesia")
            }

            Spacer(modifier = Modifier.weight(1f))

            // 3. Tombol Aksi
            Button(
                onClick = { println("Tombol Di Klik Cuyy")},
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            ) {
                Text("Edit Profile")
            }
        }
    }
}