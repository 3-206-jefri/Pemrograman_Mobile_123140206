package com.example.tugas9.ui.screens.settings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tugas9.platform.BatteryInfo
import com.example.tugas9.platform.DeviceInfo
import com.example.tugas9.ui.screens.notes.NotesUiState
import com.example.tugas9.ui.screens.notes.NotesViewModel
import org.koin.compose.koinInject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen() {
    val vm: SettingsViewModel = viewModel { SettingsViewModel() }
    val notesVM: NotesViewModel = viewModel { NotesViewModel() }
    val uiState by notesVM.uiState.collectAsStateWithLifecycle()

    val deviceInfo: DeviceInfo = koinInject()
    val batteryInfo: BatteryInfo = koinInject()

    val theme by vm.theme.collectAsStateWithLifecycle()
    val userName by vm.userName.collectAsStateWithLifecycle()
    val userEmail by vm.userEmail.collectAsStateWithLifecycle()

    val totalNotes = if (uiState is NotesUiState.Success) (uiState as NotesUiState.Success).notes.size else 0

    var editNameDialog by remember { mutableStateOf(false) }
    var editEmailDialog by remember { mutableStateOf(false) }
    var tempName by remember(userName) { mutableStateOf(userName) }
    var tempEmail by remember(userEmail) { mutableStateOf(userEmail) }

    if (editNameDialog) {
        AlertDialog(
            onDismissRequest = { editNameDialog = false },
            title = { Text("Edit Nama") },
            text = { OutlinedTextField(value = tempName, onValueChange = { tempName = it }, label = { Text("Nama") }) },
            confirmButton = { TextButton(onClick = { vm.setUserName(tempName); editNameDialog = false }) { Text("Simpan") } },
            dismissButton = { TextButton(onClick = { editNameDialog = false }) { Text("Batal") } }
        )
    }
    if (editEmailDialog) {
        AlertDialog(
            onDismissRequest = { editEmailDialog = false },
            title = { Text("Edit Email") },
            text = { OutlinedTextField(value = tempEmail, onValueChange = { tempEmail = it }, label = { Text("Email") }) },
            confirmButton = { TextButton(onClick = { vm.setUserEmail(tempEmail); editEmailDialog = false }) { Text("Simpan") } },
            dismissButton = { TextButton(onClick = { editEmailDialog = false }) { Text("Batal") } }
        )
    }

    Scaffold(topBar = { TopAppBar(title = { Text("Profile & Settings") }) }) { padding ->
        LazyColumn(Modifier.padding(padding).fillMaxSize()) {

            // ── 1. Area Profil ──
            item {
                Column(Modifier.fillMaxWidth().padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Default.AccountCircle, null, Modifier.size(80.dp), tint = MaterialTheme.colorScheme.primary)
                    Spacer(Modifier.height(8.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(userName, style = MaterialTheme.typography.headlineSmall)
                        IconButton(onClick = { editNameDialog = true }) { Icon(Icons.Default.Edit, "Edit nama", Modifier.size(18.dp)) }
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(userEmail, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        IconButton(onClick = { editEmailDialog = true }) { Icon(Icons.Default.Edit, "Edit email", Modifier.size(18.dp)) }
                    }
                    Spacer(Modifier.height(16.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(32.dp)) {
                        StatItem(label = "Total Notes", value = "$totalNotes")
                    }
                }
                HorizontalDivider()
            }

            // ── 2. Area Pengaturan Tampilan ──
            item {
                Text("Pengaturan Tampilan", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 8.dp))
            }
            item {
                SettingItem(
                    title = "Tema Aplikasi",
                    subtitle = if (theme == "dark") "Gelap" else "Terang", // ✅ Teks disesuaikan
                    icon = Icons.Default.DarkMode
                ) {
                    SegmentedButtons(
                        options = listOf("light" to "☀️", "dark" to "🌙"), // ✅ Hanya Terang & Gelap
                        selected = theme,
                        onSelect = vm::setTheme
                    )
                }
            }

            // ── 3. Area Informasi Perangkat ──
            item {
                Text(
                    "Informasi Perangkat",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 8.dp)
                )
            }
            item {
                Column(
                    modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    DeviceInfoCard(label = "Perangkat", value = deviceInfo.getDeviceName())
                    DeviceInfoCard(label = "Versi OS", value = deviceInfo.getOsVersion())
                    DeviceInfoCard(label = "Versi Aplikasi", value = deviceInfo.getAppVersion())
                    DeviceInfoCard(label = "Tipe Layar", value = if (deviceInfo.isTablet()) "Tablet" else "Phone")
                    DeviceInfoCard(label = "Baterai", value = "${batteryInfo.getBatteryLevel()}%")
                    DeviceInfoCard(label = "Status Charging", value = if (batteryInfo.isCharging()) "⚡ Mengisi daya" else "🔋 Baterai")
                }
                Spacer(Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun StatItem(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(value, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
        Text(label, style = MaterialTheme.typography.bodySmall)
    }
}

@Composable
fun SettingItem(title: String, subtitle: String, icon: ImageVector, content: @Composable () -> Unit) {
    Column(Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(icon, null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(20.dp))
            Spacer(Modifier.width(8.dp))
            Column(Modifier.weight(1f)) {
                Text(title, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Medium)
                Text(subtitle, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
        Spacer(Modifier.height(8.dp))
        content()
        Spacer(Modifier.height(8.dp))
        HorizontalDivider()
    }
}

@Composable
fun SegmentedButtons(options: List<Pair<String, String>>, selected: String, onSelect: (String) -> Unit) {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        options.forEach { (key, label) ->
            val isSelected = selected == key
            Button(
                onClick = { onSelect(key) },
                modifier = Modifier.weight(1f),
                colors = if (isSelected) ButtonDefaults.buttonColors()
                else ButtonDefaults.outlinedButtonColors(),
                contentPadding = PaddingValues(horizontal = 4.dp, vertical = 8.dp)
            ) {
                Text(label, style = MaterialTheme.typography.labelSmall)
            }
        }
    }
}

@Composable
fun DeviceInfoCard(label: String, value: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = label, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text(text = value, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Medium)
        }
    }
}