package com.example.tugas9.Gemini.Repository

import com.example.tugas9.Gemini.model.*
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

class AiRepository(private val client: HttpClient) {

    // Ganti dengan API Key Anda dari Google AI Studio
    private val apiKey = "YOUR_GEMINI_API_KEY_HERE"
    private val url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=$apiKey"

    suspend fun askChatbot(userMessage: String, notesContext: String = ""): Result<String> {
        return try {
            // PROMPT ENGINEERING (Bobot 25%)
            // Menggabungkan konteks catatan user agar AI bisa menjawab berdasarkan notes tersebut
            val systemPrompt = Part("Anda adalah asisten cerdas untuk aplikasi catatan. " +
                    "Gunakan konteks catatan berikut untuk membantu pengguna: $notesContext. " +
                    "Jawablah dengan sopan, ringkas, dan jelas.")

            val requestBody = GeminiRequest(
                system_instruction = SystemInstruction(listOf(systemPrompt)),
                contents = listOf(
                    Content(role = "user", parts = listOf(Part(userMessage)))
                )
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
            // ERROR HANDLING (Bobot 20%)
            Result.failure(e)
        }
    }
}