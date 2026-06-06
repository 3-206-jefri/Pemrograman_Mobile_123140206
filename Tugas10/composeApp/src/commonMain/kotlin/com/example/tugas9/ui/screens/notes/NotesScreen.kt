package com.example.tugas9.ui.screens.notes

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tugas9.data.model.Note
import androidx.compose.material.icons.filled.AutoAwesome

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesScreen(
    onNoteClick: (Long) -> Unit,
    onChatClick: () -> Unit
) {
    val vm: NotesViewModel = viewModel { NotesViewModel() }
    val uiState by vm.uiState.collectAsStateWithLifecycle()
    val searchQuery by vm.searchQuery.collectAsStateWithLifecycle()
    val sortOrder by vm.sortOrder.collectAsStateWithLifecycle()
    var showSortMenu by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            Column {
                TopAppBar(
                    title = { Text("📝 Notes") },
                    actions = {
                        IconButton(onClick = onChatClick) {
                            Icon(
                                Icons.Default.AutoAwesome,
                                "Gemini AI",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                        // Sort menu
                        Box {
                            IconButton(onClick = { showSortMenu = true }) {
                                Icon(Icons.Default.Sort, contentDescription = "Urutkan")
                            }
                            DropdownMenu(
                                expanded = showSortMenu,
                                onDismissRequest = { showSortMenu = false }
                            ) {
                                listOf("newest" to "Terbaru", "oldest" to "Terlama", "az" to "A–Z").forEach { (key, label) ->
                                    DropdownMenuItem(
                                        text = {
                                            Row(verticalAlignment = Alignment.CenterVertically) {
                                                if (sortOrder == key) Icon(Icons.Default.Check, null, Modifier.size(16.dp))
                                                else Spacer(Modifier.size(16.dp))
                                                Spacer(Modifier.width(8.dp))
                                                Text(label)
                                            }
                                        },
                                        onClick = { vm.onSortOrderChange(key); showSortMenu = false }
                                    )
                                }
                            }
                        }
                    }
                )
                SearchBar(query = searchQuery, onQueryChange = vm::onSearchQueryChange)
            }
        }
    ) { padding ->
        NoteListContent(
            uiState = uiState,
            modifier = Modifier.padding(padding),
            onNoteClick = onNoteClick,
            onToggleFavorite = { vm.toggleFavorite(it) },
            onDelete = { vm.deleteNote(it) },
            emptyMessage = if (searchQuery.isBlank()) "Belum ada catatan. Tekan + untuk menambah!" else "Tidak ada catatan yang cocok."
        )
    }
}

@Composable
fun SearchBar(query: String, onQueryChange: (String) -> Unit) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
        placeholder = { Text("Cari catatan...") },
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
        trailingIcon = {
            if (query.isNotEmpty()) {
                IconButton(onClick = { onQueryChange("") }) {
                    Icon(Icons.Default.Clear, contentDescription = "Hapus pencarian")
                }
            }
        },
        singleLine = true,
        shape = MaterialTheme.shapes.large
    )
}

@Composable
fun NoteListContent(
    uiState: NotesUiState,
    modifier: Modifier = Modifier,
    onNoteClick: (Long) -> Unit,
    onToggleFavorite: (Long) -> Unit,
    onDelete: (Long) -> Unit,
    emptyMessage: String = "Belum ada catatan."
) {
    when (uiState) {
        is NotesUiState.Loading -> {
            Box(modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        is NotesUiState.Empty -> {
            Box(modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Default.NoteAlt, null, Modifier.size(64.dp), tint = MaterialTheme.colorScheme.outlineVariant)
                    Spacer(Modifier.height(16.dp))
                    Text(emptyMessage, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        }
        is NotesUiState.Success -> {
            LazyColumn(
                modifier = modifier,
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(uiState.notes, key = { it.id }) { note ->
                    NoteCard(
                        note = note,
                        onClick = { onNoteClick(note.id.toLong()) },
                        onToggleFavorite = { onToggleFavorite(note.id.toLong()) },
                        onDelete = { onDelete(note.id.toLong()) }
                    )
                }
            }
        }
    }
}

@Composable
fun NoteCard(
    note: Note,
    onClick: () -> Unit,
    onToggleFavorite: () -> Unit,
    onDelete: () -> Unit
) {
    var showDeleteDialog by remember { mutableStateOf(false) }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Hapus Catatan") },
            text = { Text("Yakin ingin menghapus \"${note.title}\"?") },
            confirmButton = {
                TextButton(onClick = { showDeleteDialog = false; onDelete() }) { Text("Hapus", color = MaterialTheme.colorScheme.error) }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) { Text("Batal") }
            }
        )
    }

    Card(
        modifier = Modifier.fillMaxWidth().clickable { onClick() },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(Modifier.weight(1f)) {
                Text(note.title, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
                Spacer(Modifier.height(4.dp))
                Text(note.content, maxLines = 2, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Row {
                IconButton(onClick = onToggleFavorite) {
                    Icon(
                        imageVector = if (note.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Favorit",
                        tint = if (note.isFavorite) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                IconButton(onClick = { showDeleteDialog = true }) {
                    Icon(Icons.Default.Delete, contentDescription = "Hapus", tint = MaterialTheme.colorScheme.error)
                }
            }
        }
    }
}