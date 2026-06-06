package com.example.tugas9.ui.screens.favorites

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tugas9.ui.screens.notes.NoteListContent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(onNoteClick: (Long) -> Unit) {
    val vm: FavoritesViewModel = viewModel { FavoritesViewModel() }
    val uiState by vm.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = { TopAppBar(title = { Text("❤️ Favorites") }) }
    ) { padding ->
        NoteListContent(
            uiState = uiState,
            modifier = Modifier.padding(padding),
            onNoteClick = onNoteClick,
            onToggleFavorite = { vm.toggleFavorite(it) },
            onDelete = { vm.deleteNote(it) },
            emptyMessage = "Belum ada catatan favorit."
        )
    }
}