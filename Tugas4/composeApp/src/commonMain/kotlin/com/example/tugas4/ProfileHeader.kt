package com.example.tugas4

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import tugas4.composeapp.generated.resources.Res
import tugas4.composeapp.generated.resources.profile

@Composable
fun ProfileHeader(name: String, bio: String) {
    // Menambahkan horizontalAlignment agar semua isi Column ke tengah
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .background(Color.LightGray),
            contentAlignment = Alignment.Center // Agar icon tepat di tengah box
        ) {
            Image(
                painter = painterResource(Res.drawable.profile), // Panggil nama filemu di sini
                contentDescription = "Foto Profil",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop // Agar foto memenuhi lingkaran tanpa gepeng
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Material 3 menggunakan headlineSmall (pengganti h5)
        Text(
            text = name,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        // Material 3 menggunakan bodyMedium (pengganti body2)
        Text(
            text = bio,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )
    }
}