package com.example.tugas9.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.tugas9.data.repository.AiRepository
import com.example.tugas9.ui.components.NetworkStatusIndicator
import com.example.tugas9.ui.navigation.BottomNav
import com.example.tugas9.ui.navigation.Screen
import com.example.tugas9.ui.screens.chat.ChatScreen
import com.example.tugas9.ui.screens.chat.ChatViewModel
import com.example.tugas9.ui.screens.favorites.FavoritesScreen
import com.example.tugas9.ui.screens.notes.* // ✨ Mengimpor semua Screen modular dari package notes
import com.example.tugas9.ui.screens.settings.ProfileScreen
import org.koin.compose.koinInject

/* ──────────────────────────────────────────────
   MAIN SCREEN (NavHost)
────────────────────────────────────────────── */
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    val showFab = listOf(Screen.Notes.route, Screen.Favorites.route, Screen.Profile.route).contains(currentRoute)

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
        Column(modifier = Modifier.padding(padding)) {
            NetworkStatusIndicator()

            NavHost(
                navController = navController,
                startDestination = Screen.Notes.route,
                modifier = Modifier.weight(1f)
            ) {
                composable(Screen.Notes.route) {
                    NotesScreen(
                        onNoteClick = { id -> navController.navigate(Screen.Detail.createRoute(id)) },
                        onChatClick = { navController.navigate(Screen.Chat.route) }
                    )
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
                composable(Screen.Chat.route) {
                    val aiRepo: AiRepository = koinInject()
                    val chatVm: ChatViewModel = viewModel { ChatViewModel(aiRepo) }
                    val notesVm: NotesViewModel = viewModel { NotesViewModel() }

                    ChatScreen(
                        chatVm = chatVm,
                        notesVm = notesVm
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
        } // end Column
    }
}