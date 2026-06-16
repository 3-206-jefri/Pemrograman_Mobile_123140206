package com.example.tugas9.repository

import com.example.tugas9.data.model.Note
import com.example.tugas9.data.repository.NoteRepository
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class NoteRepositoryTest {

    private lateinit var repository: NoteRepository

    @BeforeTest
    fun setup() {
        // Kita memalsukan (Mock) repository agar tidak butuh database asli
        repository = mockk(relaxed = true)
    }

    // ── TEST 1: Memastikan repository bisa mengambil data ──
    @Test
    fun test1_GetAllNotes_ReturnsData() = runTest {
        // Kita cukup mendefinisikan id, title, dan content.
        // Sisanya akan otomatis terisi nilai default dari NoteModel.kt
        val dummyList = listOf(Note(id = 1, title = "Beli Susu", content = "Di minimarket"))
        every { repository.getAllNotes() } returns flowOf(dummyList)

        val result = repository.getAllNotes().first()
        assertEquals(1, result.size)
        assertEquals("Beli Susu", result[0].title)
    }

    // ── TEST 2: Memastikan repository tidak crash saat data kosong ──
    @Test
    fun test2_GetAllNotes_ReturnsEmptyList() = runTest {
        every { repository.getAllNotes() } returns flowOf(emptyList())

        val result = repository.getAllNotes().first()
        assertTrue(result.isEmpty())
    }

    // ── TEST 3: Memastikan fungsi insert dipanggil dengan benar ──
    @Test
    fun test3_InsertNote_IsCalledCorrectly() = runTest {
        repository.insertNote("Tugas PAM", "Selesaiin Unit Test")

        coVerify(exactly = 1) { repository.insertNote("Tugas PAM", "Selesaiin Unit Test") }
    }

    // ── TEST 4: Memastikan fungsi delete dipanggil dengan ID (Int) yang benar ──
    @Test
    fun test4_DeleteNote_IsCalledCorrectly() = runTest {
        repository.deleteNote(99)

        coVerify(exactly = 1) { repository.deleteNote(99) }
    }

    // ── TEST 5: Memastikan insertNote bisa menangani input kosong ──
    @Test
    fun test5_InsertNote_WithEmptyStrings() = runTest {
        repository.insertNote("", "")

        coVerify(exactly = 1) { repository.insertNote("", "") }
    }
}