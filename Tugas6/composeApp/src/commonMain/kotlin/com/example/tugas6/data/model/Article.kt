package com.example.tugas6.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Article(
    val id: Int,
    val userId: Int,
    val title: String,
    val body: String
) {
    val imageUrl: String
        get() = "https://picsum.photos/seed/$id/400/200"

    // Pastikan ini di baris baru, bukan nyambung di satu baris
    val description: String
        get() = if (body.length > 80) body.take(80) + "..." else body
}