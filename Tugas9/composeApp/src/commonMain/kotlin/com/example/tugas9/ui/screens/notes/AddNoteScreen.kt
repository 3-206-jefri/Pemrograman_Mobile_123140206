package com.example.tugas9.ui.screens.notes

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNoteScreen(onBack: () -> Unit) {
    val vm: NotesViewModel = viewModel { NotesViewModel() }
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Tambah Note") },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back") } }
            )
        }
    ) { padding ->
        Column(Modifier.padding(padding).padding(16.dp).fillMaxSize(), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("Judul") }, modifier = Modifier.fillMaxWidth(), singleLine = true)
            OutlinedTextField(
                value = content, onValueChange = { content = it }, label = { Text("Isi Catatan") },
                modifier = Modifier.fillMaxWidth().height(200.dp), maxLines = 10
            )
            Spacer(Modifier.weight(1f))
            Button(
                onClick = { if (title.isNotBlank()) { vm.addNote(title.trim(), content.trim()); onBack() } },
                modifier = Modifier.fillMaxWidth(),
                enabled = title.isNotBlank()
            ) { Text("Simpan") }
        }
    }
}