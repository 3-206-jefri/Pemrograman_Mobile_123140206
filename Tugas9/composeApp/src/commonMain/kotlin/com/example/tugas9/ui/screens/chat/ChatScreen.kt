package com.example.tugas9.ui.screens.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.tugas9.ui.screens.notes.NotesUiState
import com.example.tugas9.ui.screens.notes.NotesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    chatVm: ChatViewModel,
    notesVm: NotesViewModel // Kita butuh ini untuk mengambil data notes user
) {
    val chatState by chatVm.chatState.collectAsStateWithLifecycle()
    val notesUiState by notesVm.uiState.collectAsStateWithLifecycle()

    var inputText by remember { mutableStateOf("") }

    // Mengubah daftar catatan yang ada menjadi satu String panjang untuk konteks AI
    val notesContext = remember(notesUiState) {
        if (notesUiState is NotesUiState.Success) {
            val notesList = (notesUiState as NotesUiState.Success).notes
            notesList.joinToString(separator = "\n") { "- Judul: ${it.title}, Isi: ${it.content}" }
        } else {
            "Belum ada catatan."
        }
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("🤖 AI Assistant") }) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            // Area Konten / Jawaban
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                contentAlignment = Alignment.TopCenter
            ) {
                when (val state = chatState) {
                    is ChatState.Idle -> {
                        Text(
                            "Halo! Saya asisten AI. Ada yang bisa saya bantu terkait catatanmu?",
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    is ChatState.Loading -> {
                        CircularProgressIndicator(modifier = Modifier.padding(top = 32.dp))
                    }
                    is ChatState.Success -> {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(MaterialTheme.colorScheme.primaryContainer)
                                .padding(16.dp)
                                .fillMaxWidth()
                        ) {
                            Text(
                                text = state.message,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                    }
                    is ChatState.Error -> {
                        Text(
                            text = "Error: ${state.error}",
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            }

            // Area Input Pesan
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = inputText,
                    onValueChange = { inputText = it },
                    modifier = Modifier.weight(1f),
                    placeholder = { Text("Tanya ke AI...") },
                    shape = RoundedCornerShape(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                IconButton(
                    onClick = {
                        // Kirim pesan ke ViewModel beserta konteks catatannya
                        chatVm.sendMessage(message = inputText, notesData = notesContext)
                        inputText = "" // Kosongkan textfield setelah kirim
                    },
                    modifier = Modifier.background(
                        MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(24.dp)
                    ),
                    enabled = inputText.isNotBlank() && chatState !is ChatState.Loading
                ) {
                    Icon(
                        Icons.Default.Send,
                        contentDescription = "Kirim",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }
    }
}