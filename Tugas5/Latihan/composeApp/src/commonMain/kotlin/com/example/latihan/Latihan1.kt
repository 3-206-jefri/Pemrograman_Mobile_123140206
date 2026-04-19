package com.example.latihan

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun Latihan1() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            HomeScreen(
                onNavigate = { navController.navigate("detail") }
            )
        }

        composable("detail") {
            DetailScreen(
                onBack = { navController.popBackStack() }
            )
        }
    }
}

@Composable
fun HomeScreen(onNavigate: () -> Unit) {
    Button(onClick = onNavigate) {
        Text("Go to Detail")
    }
}

@Composable
fun DetailScreen(onBack: () -> Unit) {
    Button(onClick = onBack) {
        Text("Go Back")
    }
}