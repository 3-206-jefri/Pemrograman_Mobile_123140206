package com.example.tugas9

sealed class Screen(val route: String) {
    object Notes     : Screen("notes")
    object Favorites : Screen("favorites")
    object Profile   : Screen("profile")
    object AddNote   : Screen("add_note")

    object Detail : Screen("detail/{noteId}") {
        fun createRoute(noteId: Long) = "detail/$noteId"
    }
    object EditNote : Screen("edit/{noteId}") {
        fun createRoute(noteId: Long) = "edit/$noteId"
    }
}
