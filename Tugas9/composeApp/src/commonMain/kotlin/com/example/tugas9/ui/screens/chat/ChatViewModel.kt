package com.example.tugas9.ui.screens.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tugas9.data.model.Content
import com.example.tugas9.data.model.Part
import com.example.tugas9.data.repository.AiRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// Model baru untuk membedakan pesan User dan AI di layar
data class ChatMessage(val text: String, val isUser: Boolean)

class ChatViewModel(private val aiRepository: AiRepository) : ViewModel() {

    // State untuk daftar pesan di layar (dimulai dengan sapaan AI)
    private val _messages = MutableStateFlow<List<ChatMessage>>(
        listOf(ChatMessage("Halo! Saya asisten AI. Ada yang bisa saya bantu terkait catatanmu?", isUser = false))
    )
    val messages: StateFlow<List<ChatMessage>> = _messages.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    // ✨ Memori riwayat chat yang akan dikirim ke API Gemini
    private val chatHistory = mutableListOf<Content>()

    fun sendMessage(message: String, notesData: String) {
        if (message.isBlank()) return

        viewModelScope.launch {
            // 1. Tambahkan pesan User ke UI dan ke Memori
            _messages.value += ChatMessage(message, isUser = true)
            chatHistory.add(Content(role = "user", parts = listOf(Part(text = message))))

            _isLoading.value = true

            // 2. Kirim seluruh memori obrolan ke Repository
            aiRepository.askChatbot(chatHistory, notesData).fold(
                onSuccess = { reply ->
                    // 3. Tambahkan balasan AI ke UI dan ke Memori
                    _messages.value += ChatMessage(reply, isUser = false)
                    chatHistory.add(Content(role = "model", parts = listOf(Part(text = reply))))
                },
                onFailure = { exception ->
                    _messages.value += ChatMessage("Error: ${exception.message}", isUser = false)
                    // Hapus pesan user terakhir dari memori agar obrolan tidak rusak
                    chatHistory.removeLastOrNull()
                }
            )
            _isLoading.value = false
        }
    }
}