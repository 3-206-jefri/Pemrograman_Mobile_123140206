package com.example.tugas6.ui.Viewmodel

// ui/viewmodel/NewsViewModel.kt


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tugas6.data.model.Article
import com.example.tugas6.data.repository.NewsRepository
import com.example.tugas6.ui.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NewsViewModel(private val repository: NewsRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<Article>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<Article>>> = _uiState.asStateFlow()

    // Untuk pull to refresh
    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

    init {
        loadArticles()
    }

    fun loadArticles() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            repository.getArticles()
                .onSuccess { articles ->

                    _uiState.value = UiState.Success(articles)
                }
                .onFailure { error ->
                    _uiState.value = UiState.Error(
                        error.message ?: "Gagal memuat berita"
                    )
                }
        }
    }

    fun refresh() {
        viewModelScope.launch {
            _isRefreshing.value = true
            repository.getArticles()
                .onSuccess { articles ->
                    val shuffled = articles.shuffled()
                    _uiState.value = UiState.Success(shuffled)
                }
                .onFailure { error ->
                    _uiState.value = UiState.Error(
                        error.message ?: "Gagal refresh berita"
                    )
                }
            _isRefreshing.value = false
        }
    }
}