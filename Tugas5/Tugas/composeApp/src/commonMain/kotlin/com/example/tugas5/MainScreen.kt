package com.example.tugas5

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.NavType
import androidx.navigation.navArgument





/* ──────────────────────────────────────────────
   MAIN SCREEN
────────────────────────────────────────────── */
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    var noteVersion by remember { mutableIntStateOf(0) }
    val refresh: () -> Unit = { noteVersion++ }
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    val showAddFab = currentRoute == Screen.Notes.route ||
                     currentRoute == Screen.Favorites.route ||
                     currentRoute == Screen.Profile.route

    Scaffold(
        bottomBar = { BottomNav(navController) },
        floatingActionButton = {
            if (showAddFab) {
                FloatingActionButton(onClick = {
                    navController.navigate(Screen.AddNote.route)
                }) {
                    Text("+", style = MaterialTheme.typography.headlineMedium)
                }
            }
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Notes.route,
            modifier = Modifier.padding(padding)
        ) {

            composable(Screen.Notes.route) {
                NotesScreen(
                    noteVersion = noteVersion,
                    onNoteClick = { id ->
                        navController.navigate(Screen.Detail.createRoute(id))
                    }
                )
            }

            composable(Screen.Favorites.route) {
                FavoritesScreen(
                    noteVersion = noteVersion,
                    onNoteClick = { id ->
                        navController.navigate(Screen.Detail.createRoute(id))
                    }
                )
            }

            composable(Screen.Profile.route) {
                ProfileScreen()
            }

            // ── Detail Screen ──
            composable(
                route = Screen.Detail.route,
                arguments = listOf(
                    navArgument("noteId") { type = NavType.IntType }
                )
            ) { backStackEntry ->
                val noteId = backStackEntry.arguments?.getInt("noteId") ?: 0
                DetailScreen(
                    noteId = noteId,
                    onEdit = { navController.navigate(Screen.EditNote.createRoute(noteId)) },
                    onBack = { navController.popBackStack() },
                    onRefresh = refresh
                )
            }

            // ── Add Note Screen ──
            composable(Screen.AddNote.route) {
                AddNoteScreen(
                    onBack = {
                        refresh()
                        navController.popBackStack()
                    }
                )
            }

            // ── Edit Note Screen ──
            composable(
                route = Screen.EditNote.route,
                arguments = listOf(
                    navArgument("noteId") { type = NavType.IntType }
                )
            ) { backStackEntry ->
                val noteId = backStackEntry.arguments?.getInt("noteId") ?: 0
                EditNoteScreen(
                    noteId = noteId,
                    onBack = {
                        refresh()
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}

/* ──────────────────────────────────────────────
   NOTES SCREEN
────────────────────────────────────────────── */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesScreen(noteVersion: Int, onNoteClick: (Int) -> Unit) {
    val notes = remember(noteVersion) { NoteRepository.notes.toList() }

    Scaffold(
        topBar = { TopAppBar(title = { Text("📝 Notes") }) }
    ) { padding ->
        if (notes.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize().padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text("Belum ada catatan. Tekan + untuk menambah!")
            }
        } else {
            LazyColumn(
                modifier = Modifier.padding(padding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(notes, key = { it.id }) { note ->
                    NoteCard(note = note, onClick = { onNoteClick(note.id) })
                }
            }
        }
    }
}

/* ──────────────────────────────────────────────
   FAVORITES SCREEN
────────────────────────────────────────────── */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(noteVersion: Int, onNoteClick: (Int) -> Unit) {
    val favorites = remember(noteVersion) { NoteRepository.notes.filter { it.isFavorite } }

    Scaffold(
        topBar = { TopAppBar(title = { Text("❤️ Favorites") }) }
    ) { padding ->
        if (favorites.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize().padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text("Belum ada catatan favorit.")
            }
        } else {
            LazyColumn(
                modifier = Modifier.padding(padding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(favorites, key = { it.id }) { note ->
                    NoteCard(note = note, onClick = { onNoteClick(note.id) })
                }
            }
        }
    }
}

/* ──────────────────────────────────────────────
   NOTE CARD
────────────────────────────────────────────── */
@Composable
fun NoteCard(note: Note, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(Modifier.weight(1f)) {
                Text(
                    text = note.title,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = note.content,
                    maxLines = 2,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            if (note.isFavorite) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Favorit",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

/* ──────────────────────────────────────────────
   DETAIL SCREEN
────────────────────────────────────────────── */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    noteId: Int,       // ✅ ganti id → noteId
    onEdit: () -> Unit,
    onBack: () -> Unit,
    onRefresh: () -> Unit
) {
    var note by remember { mutableStateOf(NoteRepository.getById(noteId)) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detail Note") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        NoteRepository.toggleFavorite(noteId)
                        note = NoteRepository.getById(noteId)
                        onRefresh()
                    }) {
                        Icon(
                            imageVector = if (note?.isFavorite == true)
                                Icons.Default.Favorite
                            else
                                Icons.Default.FavoriteBorder,
                            contentDescription = "Toggle Favorite",
                            tint = if (note?.isFavorite == true)
                                MaterialTheme.colorScheme.error
                            else
                                MaterialTheme.colorScheme.onSurface
                        )
                    }
                    IconButton(onClick = {
                        NoteRepository.delete(noteId)
                        onRefresh()
                        onBack()
                    }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete",
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onEdit) {
                Icon(Icons.Default.Edit, contentDescription = "Edit Note")
            }
        }
    ) { padding ->
        if (note == null) {
            Box(
                modifier = Modifier.fillMaxSize().padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text("Note tidak ditemukan.")
            }
        } else {
            Column(
                modifier = Modifier
                    .padding(padding)
                    .padding(16.dp)
                    .fillMaxSize()
            ) {
                Text(
                    text = note!!.title,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.height(12.dp))
                HorizontalDivider()
                Spacer(Modifier.height(12.dp))
                Text(
                    text = note!!.content,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

/* ──────────────────────────────────────────────
   ADD NOTE SCREEN
────────────────────────────────────────────── */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNoteScreen(onBack: () -> Unit) {
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Tambah Note") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Judul") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            OutlinedTextField(
                value = content,
                onValueChange = { content = it },
                label = { Text("Isi Catatan") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                maxLines = 10
            )
            Spacer(Modifier.weight(1f))
            Button(
                onClick = {
                    if (title.isNotBlank()) {
                        NoteRepository.add(title.trim(), content.trim())
                        onBack()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = title.isNotBlank()
            ) {
                Text("Simpan")
            }
        }
    }
}

/* ──────────────────────────────────────────────
   EDIT NOTE SCREEN
────────────────────────────────────────────── */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditNoteScreen(
    noteId: Int,       // ✅ ganti id → noteId
    onBack: () -> Unit
) {
    val existing = remember { NoteRepository.getById(noteId) }
    var title by remember { mutableStateOf(existing?.title ?: "") }
    var content by remember { mutableStateOf(existing?.content ?: "") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Edit Note") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Judul") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            OutlinedTextField(
                value = content,
                onValueChange = { content = it },
                label = { Text("Isi Catatan") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                maxLines = 10
            )
            Spacer(Modifier.weight(1f))
            Button(
                onClick = {
                    if (title.isNotBlank()) {
                        NoteRepository.update(noteId, title.trim(), content.trim())
                        onBack()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = title.isNotBlank()
            ) {
                Text("Simpan Perubahan")
            }
        }
    }
}

/* ──────────────────────────────────────────────
   PROFILE SCREEN
────────────────────────────────────────────── */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen() {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Profile") }) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = null,
                modifier = Modifier.size(80.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(Modifier.height(16.dp))
            Text("Nama Pengguna", style = MaterialTheme.typography.headlineSmall)
            Text(
                text = "user@email.com",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(Modifier.height(24.dp))
            val total = NoteRepository.notes.size
            val favorites = NoteRepository.notes.count { it.isFavorite }
            Row(horizontalArrangement = Arrangement.spacedBy(32.dp)) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "$total",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text("Total Notes", style = MaterialTheme.typography.bodySmall)
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "$favorites",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text("Favorites", style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}

