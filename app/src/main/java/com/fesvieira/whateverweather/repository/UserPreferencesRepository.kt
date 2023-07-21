package com.fesvieira.whateverweather.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class UserPreferencesRepository(
    private val dataStore: DataStore<Preferences>
) {
    // KEYS
    private companion object {
        val CURRENT_CITY = stringPreferencesKey("current_city")
    }

    val currentCity: Flow<String> = dataStore.data
        .catch { emit(emptyPreferences()) }
        .map { preferences ->
            preferences[CURRENT_CITY] ?: "London"
        }

    suspend fun saveCurrentCity(city: String) {
        dataStore.edit { preferences ->
            preferences[CURRENT_CITY] = city
        }
    }
}