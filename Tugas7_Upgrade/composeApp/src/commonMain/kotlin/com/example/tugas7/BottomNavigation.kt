package com.example.tugas7

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Notes
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

sealed class BottomNavItem(val route: String, val icon: ImageVector, val label: String) {
    object Home      : BottomNavItem(Screen.Notes.route,     Icons.Default.Notes,    "Notes")
    object Favorites : BottomNavItem(Screen.Favorites.route, Icons.Default.Favorite, "Favorites")
    object Profile   : BottomNavItem(Screen.Profile.route,   Icons.Default.Person,   "Profile")
}

@Composable
fun BottomNav(navController: NavController) {
    val items = listOf(BottomNavItem.Home, BottomNavItem.Favorites, BottomNavItem.Profile)
    NavigationBar {
        val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
        items.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = { Icon(item.icon, item.label) },
                label = { Text(item.label) }
            )
        }
    }
}
