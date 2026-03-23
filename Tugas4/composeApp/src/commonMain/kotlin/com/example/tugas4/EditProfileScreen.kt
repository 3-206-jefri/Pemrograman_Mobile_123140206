package com.example.tugas4

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun EditProfileScreen(
    currentName: String,
    currentBio: String,
    onSave: (String, String) -> Unit,
    onCancel: () -> Unit
) {
    // State lokal untuk menampung input sementara
    var nameInput by remember { mutableStateOf(currentName) }
    var bioInput by remember { mutableStateOf(currentBio) }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Edit Profil", style = MaterialTheme.typography.headlineMedium)

        OutlinedTextField(
            value = nameInput,
            onValueChange = { nameInput = it },
            label = { Text("Nama Lengkap") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = bioInput,
            onValueChange = { bioInput = it },
            label = { Text("Deskripsi / Bio") },
            modifier = Modifier.fillMaxWidth()
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Tombol Batal
            OutlinedButton(onClick = onCancel, modifier = Modifier.weight(1f)) {
                Text("Batal")
            }
            // Tombol Simpan
            Button(
                onClick = { onSave(nameInput, bioInput) },
                modifier = Modifier.weight(1f)
            ) {
                Text("Simpan")
            }
        }
    }
}