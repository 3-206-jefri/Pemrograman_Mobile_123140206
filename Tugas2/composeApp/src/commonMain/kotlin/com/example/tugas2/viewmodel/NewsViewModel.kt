package com.example.tugas2.viewmodel

import com.example.tugas2.data.NewsRepository
import com.example.tugas2.model.News
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

class NewsViewModel {

    private val repository = NewsRepository()
    private val scope = CoroutineScope(Dispatchers.Default + SupervisorJob())

    private val _newsList = MutableStateFlow<List<News>>(emptyList())
    private val _selectedCategory = MutableStateFlow("All")
    private val _readCount = MutableStateFlow(0)

    val readCount: StateFlow<Int> = _readCount

    val filteredNews: StateFlow<List<News>> =
        combine(_newsList, _selectedCategory) { list, category ->
            if (category == "All") list
            else list.filter { it.category == category }
        }.stateIn(scope, SharingStarted.Eagerly, emptyList())

    init {
        scope.launch {
            repository.getNewsStream()
                .map { news ->
                    // Transform data
                    news.copy(title = news.title.uppercase())
                }
                .collect { news ->
                    _newsList.value += news
                }
        }
    }

    fun filterByCategory(category: String) {
        _selectedCategory.value = category
    }

    fun markAsRead() {
        _readCount.value++
    }

    suspend fun getDetail(news: News): String {
        return repository.getNewsDetail(news)
    }
}