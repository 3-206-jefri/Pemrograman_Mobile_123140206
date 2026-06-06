package com.example.tugas9.ui.screens.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tugas9.di.AppDependencies
import com.example.tugas9.data.model.Note
import com.example.tugas9.data.repository.NoteRepository
import com.example.tugas9.ui.screens.notes.NotesUiState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FavoritesViewModel(
    private val repository: NoteRepository = AppDependencies.noteRepository
) : ViewModel() {

    val uiState: StateFlow<NotesUiState> = repository.getFavorites()
        .map<List<Note>, NotesUiState> { notes ->
            if (notes.isEmpty()) NotesUiState.Empty else NotesUiState.Success(notes)
        }
        .onStart { emit(NotesUiState.Loading) }
        .stateIn(viewModelScope, SharingStarted.Companion.WhileSubscribed(5000), NotesUiState.Loading)

    fun toggleFavorite(id: Long) {
        viewModelScope.launch { repository.toggleFavorite(id) }
    }

    fun deleteNote(id: Long) {
        viewModelScope.launch { repository.deleteNote(id) }
    }
}