package com.example.tugas4

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun App() {
    val navController = rememberNavController()

    var isDarkMode by rememberSaveable { mutableStateOf(false) }
    var name by rememberSaveable { mutableStateOf("Jefri Wahyu Fernando Sembiring") }
    var bio by rememberSaveable { mutableStateOf("Mahasiswa informatika Institut Teknologi Sumatera") }

    val colorScheme = if (isDarkMode) darkColorScheme() else lightColorScheme()

    MaterialTheme(colorScheme = colorScheme) {
        NavHost(navController = navController, startDestination = "profile") {

            composable("profile") {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background), // Background adaptif
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(40.dp))

                    ProfileHeader(name = name, bio = bio)

                    ProfileCard {
                        InfoItem(Icons.Default.Email, "Email", "jefri.123140206@student.itera.ac.id")
                        HorizontalDivider(thickness = 0.5.dp, color = Color.LightGray)
                        InfoItem(Icons.Default.Phone, "Telepon", "+62 819 1993 1914")
                        HorizontalDivider(thickness = 0.5.dp, color = Color.LightGray)
                        InfoItem(Icons.Default.LocationOn, "Lokasi", "Lampung Selatan, Indonesia")
                    }

                    // Baris Dark Mode
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            "Mode Gelap",
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Switch(
                            checked = isDarkMode,
                            onCheckedChange = { isDarkMode = it }
                        )
                    } // <--- KURUNG PENUTUP ROW YANG TADI KURANG

                    Spacer(modifier = Modifier.weight(1f))

                    Button(
                        onClick = { navController.navigate("edit_profile") },
                        modifier = Modifier.fillMaxWidth().padding(16.dp)
                    ) {
                        Text("Edit Profile")
                    }
                }
            }

            composable("edit_profile") {
                EditProfileScreen(
                    currentName = name,
                    currentBio = bio,
                    onSave = { newName, newBio ->
                        name = newName
                        bio = newBio
                        navController.popBackStack()
                    },
                    onCancel = { navController.popBackStack() }
                )
            }
        }
    }
}