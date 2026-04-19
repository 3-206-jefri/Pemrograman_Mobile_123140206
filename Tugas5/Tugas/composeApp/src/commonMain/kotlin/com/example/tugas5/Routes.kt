package com.example.tugas5

sealed class Screen(val route: String) {
    object Notes     : Screen("notes")
    object Favorites : Screen("favorites")
    object Profile   : Screen("profile")
    object AddNote   : Screen("add_note")

    object Detail : Screen("detail/{id}") {
        fun createRoute(id: Int) = "detail/$id"
    }

    object EditNote : Screen("edit/{id}") {
        fun createRoute(id: Int) = "edit/$id"
    }
}