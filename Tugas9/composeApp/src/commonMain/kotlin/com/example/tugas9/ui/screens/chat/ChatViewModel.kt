package com.example.tugas9.ui.screens.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tugas9.data.repository.AiRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class ChatState {
    object Idle : ChatState()
    object Loading : ChatState()
    data class Success(val message: String) : ChatState()
    data class Error(val error: String) : ChatState()
}

class ChatViewModel(private val aiRepository: AiRepository) : ViewModel() {

    private val _chatState = MutableStateFlow<ChatState>(ChatState.Idle)
    val chatState: StateFlow<ChatState> = _chatState.asStateFlow()

    fun sendMessage(message: String, notesData: String) {
        viewModelScope.launch {
            _chatState.value = ChatState.Loading

            aiRepository.askChatbot(message, notesData).fold(
                onSuccess = { reply ->
                    _chatState.value = ChatState.Success(reply)
                },
                onFailure = { exception ->
                    _chatState.value = ChatState.Error(exception.message ?: "Terjadi kesalahan koneksi.")
                }
            )
        }
    }
}