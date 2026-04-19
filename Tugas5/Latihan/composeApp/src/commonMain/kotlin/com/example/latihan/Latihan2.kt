package com.example.latihan

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument


sealed class Screen(val route: String) {
    object NoteList : Screen("note_list")

    object NoteDetail : Screen("note_detail/{noteId}") {
        fun createRoute(noteId: Int) = "note_detail/$noteId"
    }


}

@Composable
fun Latihan2() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.NoteList.route
    ) {
        composable(Screen.NoteList.route) {
            NoteListScreen(
                onNoteClick = { id ->
                    navController.navigate(Screen.NoteDetail.createRoute(id))
                }
            )
        }

        composable(
            route = Screen.NoteDetail.route,
            arguments = listOf(
                navArgument("noteId") { type = NavType.IntType }
            )
        ) { backStackEntry ->

            val noteId = backStackEntry.arguments?.getInt("noteId") ?: 0

            NoteDetailScreen(
                noteId = noteId,
                onBack = { navController.popBackStack() }
            )
        }
    }
}


@Composable
fun NoteListScreen(onNoteClick: (Int) -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {

        Text("Note List")

        Button(onClick = { onNoteClick(1) }) {
            Text("Go to Note 1")
        }

        Button(onClick = { onNoteClick(2) }) {
            Text("Go to Note 2")
        }
    }
}


@Composable
fun NoteDetailScreen(noteId: Int, onBack: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {

        Text("Detail Note ID: $noteId")

        Button(onClick = onBack) {
            Text("Back")
        }
    }
}