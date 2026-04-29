package com.example.tugas7

import com.russhwolf.settings.Settings
import com.russhwolf.settings.coroutines.FlowSettings
import com.russhwolf.settings.coroutines.toFlowSettings
import kotlinx.coroutines.flow.Flow

/**
 * SettingsManager – mengelola preferensi pengguna dengan multiplatform-settings.
 * Di Android data disimpan di SharedPreferences, di iOS di NSUserDefaults.
 */
class SettingsManager(settings: Settings) {

    private val flowSettings: FlowSettings = settings.toFlowSettings()

    companion object {
        const val KEY_THEME      = "app_theme"       // "light" | "dark" | "system"
        const val KEY_SORT_ORDER = "sort_order"      // "newest" | "oldest" | "az"
        const val KEY_USER_NAME  = "user_name"
        const val KEY_USER_EMAIL = "user_email"

        const val DEFAULT_THEME      = "system"
        const val DEFAULT_SORT_ORDER = "newest"
        const val DEFAULT_USER_NAME  = "Nama Pengguna"
        const val DEFAULT_USER_EMAIL = "user@email.com"
    }

    // ── Theme ──────────────────────────────────────────────────────────────────
    val themeFlow: Flow<String> =
        flowSettings.getStringFlow(KEY_THEME, DEFAULT_THEME)

    suspend fun setTheme(theme: String) =
        flowSettings.putString(KEY_THEME, theme)

    // ── Sort Order ─────────────────────────────────────────────────────────────
    val sortOrderFlow: Flow<String> =
        flowSettings.getStringFlow(KEY_SORT_ORDER, DEFAULT_SORT_ORDER)

    suspend fun setSortOrder(order: String) =
        flowSettings.putString(KEY_SORT_ORDER, order)

    // ── Profile ────────────────────────────────────────────────────────────────
    val userNameFlow: Flow<String> =
        flowSettings.getStringFlow(KEY_USER_NAME, DEFAULT_USER_NAME)

    val userEmailFlow: Flow<String> =
        flowSettings.getStringFlow(KEY_USER_EMAIL, DEFAULT_USER_EMAIL)

    suspend fun setUserName(name: String) =
        flowSettings.putString(KEY_USER_NAME, name)

    suspend fun setUserEmail(email: String) =
        flowSettings.putString(KEY_USER_EMAIL, email)
}
