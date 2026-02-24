package com.example.tugas2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.tugas2.ui.NewsScreen
import com.example.tugas2.viewmodel.NewsViewModel
import androidx.compose.material3.MaterialTheme

class MainActivity : ComponentActivity() {

    private val viewModel = NewsViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                NewsScreen(viewModel)
            }
        }
    }
}