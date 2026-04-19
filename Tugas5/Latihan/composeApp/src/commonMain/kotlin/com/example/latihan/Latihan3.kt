package com.example.latihan

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

/* =========================
   BOTTOM NAV ITEMS
========================= */
sealed class BottomNavItem(val route: String, val label: String) {
    object Home : BottomNavItem("home", "Home")
    object Favorites : BottomNavItem("favorites", "Favorites")
    object Profile : BottomNavItem("profile", "Profile")

}

/* =========================
   MAIN SCREEN
========================= */
@Composable
fun Latihan3() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            NavigationBar {
                val items = listOf(
                    BottomNavItem.Home,
                    BottomNavItem.Favorites,
                    BottomNavItem.Profile
                )

                val currentRoute =
                    navController.currentBackStackEntryAsState().value?.destination?.route

                items.forEach { item ->
                    NavigationBarItem(
                        selected = currentRoute == item.route,
                        onClick = {
                            navController.navigate(item.route) {
                                popUpTo(navController.graph.startDestinationRoute!!)
                                launchSingleTop = true
                            }
                        },
                        label = { Text(item.label) },
                        icon = {}
                    )
                }
            }
        }
    ) { padding ->

        NavHost(
            navController = navController,
            startDestination = BottomNavItem.Home.route,
            modifier = Modifier.padding(padding)
        ) {

            composable("home") {
                Text("Home Screen")
            }

            composable("favorites") {
                Text("Favorites Screen")
            }

            composable("profile") {
                Text("Profile Screen")
            }
        }
    }
}