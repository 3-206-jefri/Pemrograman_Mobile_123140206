package com.example.tugas2.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tugas2.viewmodel.NewsViewModel
import kotlinx.coroutines.launch

@Composable
fun NewsScreen(viewModel: NewsViewModel) {

    val newsList by viewModel.filteredNews.collectAsState()
    val readCount by viewModel.readCount.collectAsState()

    val scope = rememberCoroutineScope()
    var dialogText by remember { mutableStateOf<String?>(null) }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {

        Text("Dibaca: $readCount", style = MaterialTheme.typography.titleMedium)

        Spacer(modifier = Modifier.height(12.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = { viewModel.filterByCategory("All") }) {
                Text("All")
            }
            Button(onClick = { viewModel.filterByCategory("Tech") }) {
                Text("Tech")
            }
            Button(onClick = { viewModel.filterByCategory("Sport") }) {
                Text("Sport")
            }
            Button(onClick = { viewModel.filterByCategory("Health") }) {
                Text("Health")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(newsList) { news ->
                Text(
                    text = news.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            scope.launch {
                                val detail = viewModel.getDetail(news)
                                dialogText = detail
                                viewModel.markAsRead()
                            }
                        }
                        .padding(12.dp)
                )
            }
        }
    }

    dialogText?.let {
        AlertDialog(
            onDismissRequest = { dialogText = null },
            confirmButton = {
                Button(onClick = { dialogText = null }) {
                    Text("OK")
                }
            },
            title = { Text("Detail Berita") },
            text = { Text(it) }
        )
    }
}