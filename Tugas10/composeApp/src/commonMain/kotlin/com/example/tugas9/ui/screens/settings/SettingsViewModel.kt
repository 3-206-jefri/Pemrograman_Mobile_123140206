package com.example.tugas9.ui.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tugas9.di.AppDependencies
import com.example.tugas9.data.repository.NoteRepository
import com.example.tugas9.data.local.SettingsManager
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val settingsManager: SettingsManager = AppDependencies.settingsManager,
    private val repository: NoteRepository = AppDependencies.noteRepository
) : ViewModel() {

    val theme: StateFlow<String> = settingsManager.themeFlow
        .stateIn(viewModelScope, SharingStarted.Companion.WhileSubscribed(5000), SettingsManager.Companion.DEFAULT_THEME)

    val sortOrder: StateFlow<String> = settingsManager.sortOrderFlow
        .stateIn(viewModelScope, SharingStarted.Companion.WhileSubscribed(5000), SettingsManager.Companion.DEFAULT_SORT_ORDER)

    val userName: StateFlow<String> = settingsManager.userNameFlow
        .stateIn(viewModelScope, SharingStarted.Companion.WhileSubscribed(5000), SettingsManager.Companion.DEFAULT_USER_NAME)

    val userEmail: StateFlow<String> = settingsManager.userEmailFlow
        .stateIn(viewModelScope, SharingStarted.Companion.WhileSubscribed(5000), SettingsManager.Companion.DEFAULT_USER_EMAIL)

    fun setTheme(theme: String) = viewModelScope.launch { settingsManager.setTheme(theme) }
    fun setSortOrder(order: String) = viewModelScope.launch { settingsManager.setSortOrder(order) }
    fun setUserName(name: String) = viewModelScope.launch { settingsManager.setUserName(name) }
    fun setUserEmail(email: String) = viewModelScope.launch { settingsManager.setUserEmail(email) }
}