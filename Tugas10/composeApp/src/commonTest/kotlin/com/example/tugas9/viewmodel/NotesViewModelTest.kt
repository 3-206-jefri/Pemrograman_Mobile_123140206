package com.example.tugas9.viewmodel

import app.cash.turbine.test
import com.example.tugas9.data.local.SettingsManager
import com.example.tugas9.data.model.Note
import com.example.tugas9.data.repository.NoteRepository
import com.example.tugas9.ui.screens.notes.NotesUiState
import com.example.tugas9.ui.screens.notes.NotesViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class NotesViewModelTest {

    // 1. Persiapan variabel tiruan (Mock)
    private lateinit var repository: NoteRepository
    private lateinit var settingsManager: SettingsManager
    private lateinit var viewModel: NotesViewModel
    private val testDispatcher = StandardTestDispatcher()

    @BeforeTest
    fun setup() {
        // Mengganti Main thread ke test thread agar coroutine bisa diuji
        Dispatchers.setMain(testDispatcher)

        // Memalsukan (Mock) repository agar tidak memakai database asli
        repository = mockk(relaxed = true)
        settingsManager = mockk(relaxed = true)

        // Mengatur respons default dari tiruan
        val dummyNotes = listOf(Note(1, "Test Judul", "Test Isi", 123L, 123L, false))
        every { repository.getAllNotes() } returns flowOf(dummyNotes)
        every { settingsManager.sortOrderFlow } returns MutableStateFlow("newest")

        // Inisialisasi ViewModel dengan memasukkan data palsu
        viewModel = NotesViewModel(repository, settingsManager)
    }

    @AfterTest
    fun tearDown() {
        // Mengembalikan thread ke semula
        Dispatchers.resetMain()
    }

    // ── 1. FLOW TEST DENGAN TURBINE
    @Test
    fun testSearchQueryFlowUpdatesCorrectly() = runTest {
        viewModel.searchQuery.test {
            // Memeriksa state awal
            assertEquals("", awaitItem())

            // Mensimulasikan pengguna mengetik di kotak pencarian
            viewModel.onSearchQueryChange("KMP")

            // Memeriksa apakah Flow berubah menjadi "KMP"
            assertEquals("KMP", awaitItem())

            cancelAndIgnoreRemainingEvents()
        }
    }

    // ── 2. FLOW TEST STATE (Syarat Tugas 10) ──
    @Test
    fun testInitialUiStateIsLoading() = runTest {
        viewModel.uiState.test {
            // Memastikan status pertama saat aplikasi dibuka adalah Loading
            val state = awaitItem()
            assertEquals(NotesUiState.Loading, state)
            cancelAndIgnoreRemainingEvents()
        }
    }

    // ── 3. UNIT TEST MOCKK (Simulasi Tambah Data) ──
    @Test
    fun testAddNoteCallsRepository() = runTest {
        val title = "Catatan Baru"
        val content = "Ini isi catatan"

        viewModel.addNote(title, content)

        // Menunggu semua proses background selesai
        advanceUntilIdle()

        // Memastikan (Verify) bahwa fungsi insertNote di repository dipanggil tepat 1 kali
        coVerify(exactly = 1) { repository.insertNote(title, content) }
    }

    // ── 4. UNIT TEST MOCKK (Simulasi Hapus Data) ──
    @Test
    fun testDeleteNoteCallsRepository() = runTest {
        val noteId = 99L

        viewModel.deleteNote(noteId)
        advanceUntilIdle()

        // Memastikan (Verify) bahwa fungsi deleteNote di repository dipanggil
        coVerify(exactly = 1) { repository.deleteNote(noteId) }
    }
}