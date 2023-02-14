package com.example.notebookrebuild2


import androidx.datastore.DataStore
import androidx.datastore.preferences.Preferences
import androidx.datastore.preferences.preferencesKey
import androidx.datastore.preferences.toMutablePreferences
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingsMaster @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {

    private val DARK_THEME = preferencesKey<Boolean>("dark_Theme")

    suspend fun saveTheme(isDarkTheme: Boolean) {
        dataStore.updateData {
            it.toMutablePreferences().apply {
                set(DARK_THEME, isDarkTheme)
            }
        }
    }

    fun isDarkThemeFlow() = dataStore.data.map { it.get(DARK_THEME) ?: false }
}