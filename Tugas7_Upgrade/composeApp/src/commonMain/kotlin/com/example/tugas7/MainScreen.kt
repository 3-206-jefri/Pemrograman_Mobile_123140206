package com.example.tugas7

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument

/* ──────────────────────────────────────────────
   MAIN SCREEN (NavHost)
────────────────────────────────────────────── */
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    val showFab = currentRoute in listOf(Screen.Notes.route, Screen.Favorites.route, Screen.Profile.route)

    Scaffold(
        bottomBar = { BottomNav(navController) },
        floatingActionButton = {
            if (showFab) {
                FloatingActionButton(onClick = { navController.navigate(Screen.AddNote.route) }) {
                    Icon(Icons.Default.Add, contentDescription = "Tambah Note")
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
                NotesScreen(onNoteClick = { id -> navController.navigate(Screen.Detail.createRoute(id)) })
            }
            composable(Screen.Favorites.route) {
                FavoritesScreen(onNoteClick = { id -> navController.navigate(Screen.Detail.createRoute(id)) })
            }
            composable(Screen.Profile.route) {
                ProfileScreen()
            }
            composable(
                route = Screen.Detail.route,
                arguments = listOf(navArgument("noteId") { type = NavType.LongType })
            ) { back ->
                val noteId = back.arguments?.getLong("noteId") ?: 0L
                DetailScreen(
                    noteId = noteId,
                    onEdit = { navController.navigate(Screen.EditNote.createRoute(noteId)) },
                    onBack = { navController.popBackStack() }
                )
            }
            composable(Screen.AddNote.route) {
                AddNoteScreen(onBack = { navController.popBackStack() })
            }
            composable(
                route = Screen.EditNote.route,
                arguments = listOf(navArgument("noteId") { type = NavType.LongType })
            ) { back ->
                val noteId = back.arguments?.getLong("noteId") ?: 0L
                EditNoteScreen(noteId = noteId, onBack = { navController.popBackStack() })
            }
        }
    }
}

/* ──────────────────────────────────────────────
   NOTES SCREEN – dengan Search & Sort
────────────────────────────────────────────── */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesScreen(onNoteClick: (Long) -> Unit) {
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
                // Search bar
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

/* ──────────────────────────────────────────────
   SEARCH BAR
────────────────────────────────────────────── */
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

/* ──────────────────────────────────────────────
   NOTE LIST CONTENT (reusable)
────────────────────────────────────────────── */
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

/* ──────────────────────────────────────────────
   FAVORITES SCREEN
────────────────────────────────────────────── */
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

/* ──────────────────────────────────────────────
   NOTE CARD
────────────────────────────────────────────── */
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

/* ──────────────────────────────────────────────
   DETAIL SCREEN
────────────────────────────────────────────── */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(noteId: Long, onEdit: () -> Unit, onBack: () -> Unit) {
    val vm: NotesViewModel = viewModel { NotesViewModel() }
    var note by remember { mutableStateOf<Note?>(null) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(noteId) {
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
                        // Refresh note
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

/* ──────────────────────────────────────────────
   ADD NOTE SCREEN
────────────────────────────────────────────── */
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

/* ──────────────────────────────────────────────
   EDIT NOTE SCREEN
────────────────────────────────────────────── */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditNoteScreen(noteId: Long, onBack: () -> Unit) {
    val vm: NotesViewModel = viewModel { NotesViewModel() }
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    var loaded by remember { mutableStateOf(false) }

    LaunchedEffect(noteId) {
        val note = AppDependencies.noteRepository.getNoteById(noteId)
        title = note?.title ?: ""
        content = note?.content ?: ""
        loaded = true
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Edit Note") },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back") } }
            )
        }
    ) { padding ->
        if (!loaded) {
            Box(Modifier.fillMaxSize().padding(padding), Alignment.Center) { CircularProgressIndicator() }
        } else {
            Column(Modifier.padding(padding).padding(16.dp).fillMaxSize(), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("Judul") }, modifier = Modifier.fillMaxWidth(), singleLine = true)
                OutlinedTextField(
                    value = content, onValueChange = { content = it }, label = { Text("Isi Catatan") },
                    modifier = Modifier.fillMaxWidth().height(200.dp), maxLines = 10
                )
                Spacer(Modifier.weight(1f))
                Button(
                    onClick = { if (title.isNotBlank()) { vm.updateNote(noteId, title.trim(), content.trim()); onBack() } },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = title.isNotBlank()
                ) { Text("Simpan Perubahan") }
            }
        }
    }
}

/* ──────────────────────────────────────────────
   PROFILE SCREEN – dengan Settings
────────────────────────────────────────────── */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen() {
    val vm: SettingsViewModel = viewModel { SettingsViewModel() }
    val notesVM: NotesViewModel = viewModel { NotesViewModel() }
    val uiState by notesVM.uiState.collectAsStateWithLifecycle()

    val theme by vm.theme.collectAsStateWithLifecycle()
    val sortOrder by vm.sortOrder.collectAsStateWithLifecycle()
    val userName by vm.userName.collectAsStateWithLifecycle()
    val userEmail by vm.userEmail.collectAsStateWithLifecycle()

    val totalNotes = if (uiState is NotesUiState.Success) (uiState as NotesUiState.Success).notes.size else 0

    var editNameDialog by remember { mutableStateOf(false) }
    var editEmailDialog by remember { mutableStateOf(false) }
    var tempName by remember(userName) { mutableStateOf(userName) }
    var tempEmail by remember(userEmail) { mutableStateOf(userEmail) }

    // Edit Name Dialog
    if (editNameDialog) {
        AlertDialog(
            onDismissRequest = { editNameDialog = false },
            title = { Text("Edit Nama") },
            text = { OutlinedTextField(value = tempName, onValueChange = { tempName = it }, label = { Text("Nama") }) },
            confirmButton = { TextButton(onClick = { vm.setUserName(tempName); editNameDialog = false }) { Text("Simpan") } },
            dismissButton = { TextButton(onClick = { editNameDialog = false }) { Text("Batal") } }
        )
    }
    // Edit Email Dialog
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
            // ── Profile section ──
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

            // ── Settings section ──
            item {
                Text("Pengaturan Tampilan", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 8.dp))
            }
            item {
                SettingItem(
                    title = "Tema Aplikasi",
                    subtitle = when (theme) { "light" -> "Terang"; "dark" -> "Gelap"; else -> "Ikuti Sistem" },
                    icon = Icons.Default.DarkMode
                ) {
                    SegmentedButtons(
                        options = listOf("light" to "☀️", "system" to "🖥️", "dark" to "🌙"),
                        selected = theme,
                        onSelect = vm::setTheme
                    )
                }
            }
            item {
                SettingItem(
                    title = "Urutan Catatan",
                    subtitle = when (sortOrder) { "oldest" -> "Terlama"; "az" -> "A–Z"; else -> "Terbaru" },
                    icon = Icons.Default.Sort
                ) {
                    SegmentedButtons(
                        options = listOf("newest" to "Terbaru", "oldest" to "Terlama", "az" to "A–Z"),
                        selected = sortOrder,
                        onSelect = vm::setSortOrder
                    )
                }
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
fun SettingItem(title: String, subtitle: String, icon: androidx.compose.ui.graphics.vector.ImageVector, content: @Composable () -> Unit) {
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
