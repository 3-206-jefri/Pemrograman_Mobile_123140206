package com.example.tugas6



import androidx.compose.runtime.*
import com.example.tugas6.data.model.Article
import com.example.tugas6.data.repository.NewsRepository
import com.example.tugas6.ui.Screen.NewsDetailScreen
import com.example.tugas6.ui.Screen.NewsListScreen
import com.example.tugas6.ui.Viewmodel.NewsViewModel


@Composable
fun App() {
    val client = remember { HttpClientFactory.create() }
    val repository = remember { NewsRepository(client) }
    val viewModel = remember { NewsViewModel(repository) }

    // Simple navigation state
    var selectedArticle by remember { mutableStateOf<Article?>(null) }

    if (selectedArticle == null) {
        NewsListScreen(
            viewModel = viewModel,
            onArticleClick = { article ->
                selectedArticle = article
            }
        )
    } else {
        NewsDetailScreen(
            article = selectedArticle!!,
            onBack = { selectedArticle = null }
        )
    }
}