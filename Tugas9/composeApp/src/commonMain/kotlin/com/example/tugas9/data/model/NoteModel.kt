package com.example.tugas9.data.model

// Domain model Note (dipakai di seluruh UI)
data class Note(
    val id: Int,
    val title: String,
    val content: String,
    val isFavorite: Boolean = false,
    val createdAt: Long = 0L,
    val updatedAt: Long = 0L
)
