package com.example.tugas9.data.repository

import com.example.tugas9.BuildKonfig
import com.example.tugas9.data.model.Content
import com.example.tugas9.data.model.GeminiRequest
import com.example.tugas9.data.model.GeminiResponse
import com.example.tugas9.data.model.Part
import com.example.tugas9.data.model.SystemInstruction
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

class AiRepository(private val client: HttpClient) {

    // Ganti dengan API Key Anda dari Google AI Studio
    private val apiKey = BuildKonfig.GEMINI_API_KEY
    private val url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-3.1-flash-lite:generateContent?key=$apiKey"

    suspend fun askChatbot(chatHistory : List<Content>, notesContext: String = ""): Result<String> {
        return try {
            // PROMPT ENGINEERING (Bobot 25%)
            // Menggabungkan konteks catatan user agar AI bisa menjawab berdasarkan notes tersebut
            val systemPrompt = Part(
                "Anda adalah asisten cerdas untuk aplikasi catatan. " +
                        "Gunakan konteks catatan berikut untuk membantu pengguna: $notesContext. " +
                        "Jawablah dengan sopan, ringkas, dan jelas."
            )

            val requestBody = GeminiRequest(
                system_instruction = SystemInstruction(listOf(systemPrompt)),
                contents = chatHistory
                )


            val response = client.post(url) {
                contentType(ContentType.Application.Json)
                setBody(requestBody)
            }

            if (response.status.isSuccess()) {
                val geminiResponse: GeminiResponse = response.body()
                val botReply = geminiResponse.candidates?.firstOrNull()?.content?.parts?.firstOrNull()?.text

                if (botReply != null) Result.success(botReply)
                else Result.failure(Exception("Respons AI kosong."))
            } else {
                val errorResponse: GeminiResponse = response.body()
                Result.failure(Exception(errorResponse.error?.message ?: "Gagal menghubungi AI"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
