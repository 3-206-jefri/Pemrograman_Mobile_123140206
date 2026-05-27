package com.example.tugas9

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.example.tugas9.db.NotesDatabase
import com.example.tugas9.db.NoteEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock

class NoteRepository(driverFactory: DatabaseDriverFactory) {

    private val database = NotesDatabase(driverFactory.createDriver())
    private val queries = database.noteQueries

    // ── Helpers ──────────────────────────────────────────────────────────────

    private fun NoteEntity.toDomain() = Note(
        id = id.toInt(),
        title = title,
        content = content,
        isFavorite = isFavorite != 0L,
        createdAt = createdAt,
        updatedAt = updatedAt
    )

    private fun now() = Clock.System.now().toEpochMilliseconds()

    // ── Queries (Flow – reactive) ─────────────────────────────────────────────

    fun getAllNotes(): Flow<List<Note>> =
        queries.selectAll()
            .asFlow()
            .mapToList(Dispatchers.IO)
            .map { list -> list.map { it.toDomain() } }

    fun getFavorites(): Flow<List<Note>> =
        queries.selectFavorites()
            .asFlow()
            .mapToList(Dispatchers.IO)
            .map { list -> list.map { it.toDomain() } }

    fun searchNotes(query: String): Flow<List<Note>> =
        queries.search(query, query)
            .asFlow()
            .mapToList(Dispatchers.IO)
            .map { list -> list.map { it.toDomain() } }

    // ── One-shot ──────────────────────────────────────────────────────────────

    suspend fun getNoteById(id: Long): Note? = withContext(Dispatchers.IO) {
        queries.selectById(id).executeAsOneOrNull()?.toDomain()
    }

    suspend fun insertNote(title: String, content: String) = withContext(Dispatchers.IO) {
        val ts = now()
        queries.insert(title, content, ts, ts)
    }

    suspend fun updateNote(id: Long, title: String, content: String) = withContext(Dispatchers.IO) {
        queries.update(title, content, now(), id)
    }

    suspend fun toggleFavorite(id: Long) = withContext(Dispatchers.IO) {
        queries.toggleFavorite(id)
    }

    suspend fun deleteNote(id: Long) = withContext(Dispatchers.IO) {
        queries.delete(id)
    }

    suspend fun countAll(): Long = withContext(Dispatchers.IO) {
        queries.countAll().executeAsOne()
    }

    suspend fun countFavorites(): Long = withContext(Dispatchers.IO) {
        queries.countFavorites().executeAsOne()
    }
}
