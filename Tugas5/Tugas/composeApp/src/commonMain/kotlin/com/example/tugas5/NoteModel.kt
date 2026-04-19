package com.example.tugas5

// Model data Note
data class Note(
    val id: Int,
    val title: String,
    val content: String,
    val isFavorite: Boolean = false
)

// Shared in-memory state (sederhana tanpa ViewModel/DB)
object NoteRepository {
    private var nextId = 4

    val notes = mutableListOf(
        Note(1, "Belajar Jetpack Compose", "Compose adalah UI toolkit modern untuk Android.", true),
        Note(2, "Meeting Besok",           "Jam 09.00 di ruang rapat lantai 3.",             false),
        Note(3, "Resep Kopi",              "Espresso 30ml + susu 150ml + gula 1 sdt.",       true),
    )

    fun getById(id: Int): Note? = notes.find { it.id == id }

    fun add(title: String, content: String) {
        notes.add(Note(id = nextId++, title = title, content = content))
    }

    fun update(id: Int, title: String, content: String) {
        val idx = notes.indexOfFirst { it.id == id }
        if (idx != -1) notes[idx] = notes[idx].copy(title = title, content = content)
    }

    fun toggleFavorite(id: Int) {
        val idx = notes.indexOfFirst { it.id == id }
        if (idx != -1) notes[idx] = notes[idx].copy(isFavorite = !notes[idx].isFavorite)
    }

    fun delete(id: Int) = notes.removeAll { it.id == id }
}