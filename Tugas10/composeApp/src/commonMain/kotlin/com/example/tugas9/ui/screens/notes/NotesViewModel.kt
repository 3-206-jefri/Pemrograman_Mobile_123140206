package com.example.tugas9.ui.screens.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tugas9.data.repository.NoteRepository
import com.example.tugas9.data.model.Note
import com.example.tugas9.di.AppDependencies
import com.example.tugas9.data.local.SettingsManager
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

// UI State untuk layar Notes
sealed class NotesUiState {
    object Loading : NotesUiState()
    object Empty   : NotesUiState()
    data class Success(val notes: List<Note>) : NotesUiState()
}

@OptIn(ExperimentalCoroutinesApi::class)
class NotesViewModel(
    private val repository: NoteRepository = AppDependencies.noteRepository,
    private val settingsManager: SettingsManager = AppDependencies.settingsManager
) : ViewModel() {

    // Query pencarian
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()
    private val _selectedNote = MutableStateFlow<Note?>(null)
    val selectedNote: StateFlow<Note?> = _selectedNote.asStateFlow()

    // Sort order dari settings
    val sortOrder: StateFlow<String> = settingsManager.sortOrderFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "newest")

    // Daftar notes reaktif – berubah otomatis saat query/sort berubah
    val uiState: StateFlow<NotesUiState> = _searchQuery
        .flatMapLatest { query ->
            if (query.isBlank()) repository.getAllNotes()
            else repository.searchNotes(query)
        }
        .combine(sortOrder) { notes, sort ->
            when (sort) {
                "oldest" -> notes.sortedBy { it.updatedAt }
                "az"     -> notes.sortedBy { it.title }
                else     -> notes // newest (default dari DB sudah DESC)
            }
        }
        .map<List<Note>, NotesUiState> { notes ->
            if (notes.isEmpty()) NotesUiState.Empty else NotesUiState.Success(notes)
        }
        .onStart { emit(NotesUiState.Loading) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), NotesUiState.Loading)

    fun fetchNoteDetail(id: Long) {
        viewModelScope.launch {
            _selectedNote.value = repository.getNoteById(id)
        }
    }
    fun clearSelectedNote() {
        _selectedNote.value = null}
    fun onSearchQueryChange(query: String) { _searchQuery.value = query }

    fun addNote(title: String, content: String) {
        viewModelScope.launch { repository.insertNote(title, content) }
    }

    fun updateNote(id: Long, title: String, content: String) {
        viewModelScope.launch { repository.updateNote(id, title, content) }
    }

    fun deleteNote(id: Long) {
        viewModelScope.launch { repository.deleteNote(id) }
    }

    fun toggleFavorite(id: Long) {
        viewModelScope.launch { repository.toggleFavorite(id) }
    }

    fun onSortOrderChange(order: String) {
        viewModelScope.launch { settingsManager.setSortOrder(order) }
    }
}
