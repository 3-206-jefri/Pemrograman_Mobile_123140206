// Unslash semua code ini jika ingin running

//package com.example.latihan

//import androidx.navigation.navArgument
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.foundation.layout.*
//import androidx.compose.ui.Modifier
//import androidx.navigation.NavType
//import androidx.navigation.compose.*
//
///* =========================
//   ROUTES (LATIHAN 2)
//========================= */
//sealed class Screen(val route: String) {
//    object Home : Screen("home")
//    object Detail : Screen("detail")
//
//    object NoteList : Screen("note_list")
//
//    object NoteDetail : Screen("note_detail/{noteId}") {
//        fun createRoute(noteId: Int) = "note_detail/$noteId"
//    }
//}
//
///* =========================
//   BOTTOM NAV (LATIHAN 3)
//========================= */
//sealed class BottomNavItem(val route: String, val label: String) {
//    object Home : BottomNavItem("home_tab", "Home")
//    object Notes : BottomNavItem("notes_tab", "Notes")
//    object Profile : BottomNavItem("profile_tab", "Profile")
//}
//
///* =========================
//   MAIN SCREEN (LATIHAN 3)
//========================= */
//@Composable
//fun MainScreen() {
//    val navController = rememberNavController()
//
//    Scaffold(
//        bottomBar = {
//            NavigationBar {
//                val items = listOf(
//                    BottomNavItem.Home,
//                    BottomNavItem.Notes,
//                    BottomNavItem.Profile
//                )
//
//                val currentRoute =
//                    navController.currentBackStackEntryAsState().value?.destination?.route
//
//                items.forEach { item ->
//                    NavigationBarItem(
//                        selected = currentRoute == item.route,
//                        onClick = {
//                            navController.navigate(item.route) {
//                                popUpTo(navController.graph.startDestinationRoute!!)
//                                launchSingleTop = true
//                            }
//                        },
//                        label = { Text(item.label) },
//                        icon = {}
//                    )
//                }
//            }
//        }
//    ) { padding ->
//
//        NavHost(
//            navController = navController,
//            startDestination = BottomNavItem.Home.route,
//            modifier = Modifier.padding(padding)
//        ) {
//
//            /* =========================
//               TAB HOME (LATIHAN 1)
//            ========================= */
//            composable(BottomNavItem.Home.route) {
//                HomeScreen(
//                    onNavigate = { navController.navigate(Screen.Detail.route) }
//                )
//            }
//
//            composable(Screen.Detail.route) {
//                DetailScreen(
//                    onBack = { navController.popBackStack() }
//                )
//            }
//
//            /* =========================
//               TAB NOTES (LATIHAN 2)
//            ========================= */
//            composable(BottomNavItem.Notes.route) {
//                NoteListScreen(
//                    onNoteClick = { id ->
//                        navController.navigate(Screen.NoteDetail.createRoute(id))
//                    }
//                )
//            }
//
//            composable(
//                route = Screen.NoteDetail.route,
//                arguments = listOf(
//                    navArgument("noteId") { type = NavType.IntType }
//                )
//            ) { backStackEntry ->
//
//                val noteId = backStackEntry.arguments?.getInt("noteId") ?: 0
//
//                NoteDetailScreen(
//                    noteId = noteId,
//                    onBack = { navController.popBackStack() }
//                )
//            }
//
//            /* =========================
//               TAB PROFILE (LATIHAN 3)
//            ========================= */
//            composable(BottomNavItem.Profile.route) {
//                ProfileScreen()
//            }
//        }
//    }
//}
//
///* =========================
//   LATIHAN 1 UI
//========================= */
//@Composable
//fun HomeScreen(onNavigate: () -> Unit) {
//    Column(modifier = Modifier.fillMaxSize()) {
//        Text("Home Screen")
//        Button(onClick = onNavigate) {
//            Text("Go to Detail")
//        }
//    }
//}
//
//@Composable
//fun DetailScreen(onBack: () -> Unit) {
//    Column(modifier = Modifier.fillMaxSize()) {
//        Text("Detail Screen")
//        Button(onClick = onBack) {
//            Text("Back")
//        }
//    }
//}
//
///* =========================
//   LATIHAN 2 UI
//========================= */
//@Composable
//fun NoteListScreen(onNoteClick: (Int) -> Unit) {
//    Column(modifier = Modifier.fillMaxSize()) {
//        Text("Note List")
//
//        Button(onClick = { onNoteClick(1) }) {
//            Text("Note 1")
//        }
//
//        Button(onClick = { onNoteClick(2) }) {
//            Text("Note 2")
//        }
//    }
//}
//
//@Composable
//fun NoteDetailScreen(noteId: Int, onBack: () -> Unit) {
//    Column(modifier = Modifier.fillMaxSize()) {
//        Text("Detail Note ID: $noteId")
//
//        Button(onClick = onBack) {
//            Text("Back")
//        }
//    }
//}
//
///* =========================
//   LATIHAN 3 UI
//========================= */
//@Composable
//fun ProfileScreen() {
//    Column(modifier = Modifier.fillMaxSize()) {
//        Text("Profile Screen")
//    }
//}