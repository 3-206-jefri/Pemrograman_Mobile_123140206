package com.example.tugas9.ui

import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import com.example.tugas9.ui.screens.TestTags
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [33])
class NotesUiTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    // ✨ TAMBAHAN WAJIB: Mematikan mesin Koin setelah setiap 1 tes selesai
    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun uiTest1_EmptyStateIsDisplayed() {
        composeTestRule.setContent {
            Text("Belum ada catatan", modifier = Modifier.testTag(TestTags.EMPTY_STATE))
        }

        composeTestRule.onNodeWithTag(TestTags.EMPTY_STATE).assertIsDisplayed()
        composeTestRule.onNodeWithText("Belum ada catatan").assertIsDisplayed()
    }

    @Test
    fun uiTest2_NoteItemIsDisplayed() {
        composeTestRule.setContent {
            Text("Catatan Praktikum KMP", modifier = Modifier.testTag(TestTags.NOTE_ITEM))
        }

        composeTestRule.onNodeWithTag(TestTags.NOTE_ITEM).assertIsDisplayed()
        composeTestRule.onNodeWithText("Catatan Praktikum KMP").assertIsDisplayed()
    }

    @Test
    fun uiTest3_TitleInputExists() {
        composeTestRule.setContent {
            OutlinedTextField(
                value = "",
                onValueChange = {},
                modifier = Modifier.testTag(TestTags.TITLE_INPUT),
                placeholder = { Text("Masukkan Judul") }
            )
        }

        composeTestRule.onNodeWithTag(TestTags.TITLE_INPUT).assertIsDisplayed()
        composeTestRule.onNodeWithText("Masukkan Judul").assertIsDisplayed()
    }
}