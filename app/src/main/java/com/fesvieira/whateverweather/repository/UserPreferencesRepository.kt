package com.fesvieira.whateverweather.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.preferencesOf
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
        val USE_CELSIUS = booleanPreferencesKey("use_celsius")
    }

    // GETTERS
    val currentCity: Flow<String> = dataStore.data
        .catch { emit(emptyPreferences()) }
        .map { preferences ->
            preferences[CURRENT_CITY] ?: "London"
        }

    val useCelsius: Flow<Boolean> = dataStore.data
        .catch { emit(emptyPreferences()) }
        .map { preferences ->
            preferences[USE_CELSIUS] ?: false
        }

    // SETTERS
    suspend fun saveCurrentCity(city: String) {
        dataStore.edit { preferences ->
            preferences[CURRENT_CITY] = city
        }
    }

    suspend fun saveUseCelsius(useCelsius: Boolean) {
        dataStore.edit { preferences ->
            preferences[USE_CELSIUS] = useCelsius
        }
    }
}