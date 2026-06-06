package com.example.tugas9.ui.screens.notes

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tugas9.data.model.Note
import com.example.tugas9.di.AppDependencies

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(noteId: Long, onEdit: () -> Unit, onBack: () -> Unit) {
    val vm: NotesViewModel = viewModel { NotesViewModel() }
    var note by remember { mutableStateOf<Note?>(null) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(noteId) {
        // Catatan Evaluasi: Kedepannya pemanggilan repository ini sebaiknya dipindah ke dalam ViewModel
        note = AppDependencies.noteRepository.getNoteById(noteId)
        isLoading = false
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detail Note") },
                navigationIcon = {
                    IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back") }
                },
                actions = {
                    IconButton(onClick = {
                        note?.let { vm.toggleFavorite(it.id.toLong()) }
                    }) {
                        Icon(
                            imageVector = if (note?.isFavorite == true) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = "Favorit",
                            tint = if (note?.isFavorite == true) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface
                        )
                    }
                    IconButton(onClick = {
                        note?.let { vm.deleteNote(it.id.toLong()) }
                        onBack()
                    }) {
                        Icon(Icons.Default.Delete, "Hapus", tint = MaterialTheme.colorScheme.error)
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onEdit) {
                Icon(Icons.Default.Edit, "Edit")
            }
        }
    ) { padding ->
        when {
            isLoading -> Box(Modifier.fillMaxSize().padding(padding), Alignment.Center) { CircularProgressIndicator() }
            note == null -> Box(Modifier.fillMaxSize().padding(padding), Alignment.Center) { Text("Note tidak ditemukan.") }
            else -> Column(Modifier.padding(padding).padding(16.dp).fillMaxSize()) {
                Text(note!!.title, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(12.dp))
                HorizontalDivider()
                Spacer(Modifier.height(12.dp))
                Text(note!!.content, style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}