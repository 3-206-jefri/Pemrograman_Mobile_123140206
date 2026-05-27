package com.example.tugas9.Gemini.model

import kotlinx.serialization.Serializable

@Serializable
data class GeminiRequest(
    val contents: List<Content>,
    val system_instruction: SystemInstruction? = null
)

@Serializable
data class SystemInstruction(val parts: List<Part>)

@Serializable
data class Content(
    val role: String = "user",
    val parts: List<Part>
)

@Serializable
data class Part(val text: String)

@Serializable
data class GeminiResponse(
    val candidates: List<Candidate>? = null,
    val error: GeminiError? = null
)

@Serializable
data class Candidate(val content: Content)

@Serializable
data class GeminiError(val message: String)