package com.example.skytracker.data

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.settingsDataStore by preferencesDataStore(name = "settings")

data class AppSettings(
    val language: String = "es",
    val isDarkMode: Boolean = false,
    val temperatureUnit: String = "C",
    val cityName: String = "Tampico",
    val cityRegion: String = "Tamaulipas",
    val cityCountry: String = "México",
    val latitude: Double = 22.2331,
    val longitude: Double = -97.8611
)

class SettingsStore(private val context: Context) {

    companion object {
        private val LANGUAGE_KEY = stringPreferencesKey("language")
        private val DARK_MODE_KEY = booleanPreferencesKey("dark_mode")
        private val TEMPERATURE_UNIT_KEY = stringPreferencesKey("temperature_unit")

        private val CITY_NAME_KEY = stringPreferencesKey("city_name")
        private val CITY_REGION_KEY = stringPreferencesKey("city_region")
        private val CITY_COUNTRY_KEY = stringPreferencesKey("city_country")
        private val LATITUDE_KEY = doublePreferencesKey("latitude")
        private val LONGITUDE_KEY = doublePreferencesKey("longitude")
    }

    val settings: Flow<AppSettings> = context.settingsDataStore.data.map { preferences ->
        AppSettings(
            language = preferences[LANGUAGE_KEY] ?: "es",
            isDarkMode = preferences[DARK_MODE_KEY] ?: false,
            temperatureUnit = preferences[TEMPERATURE_UNIT_KEY] ?: "C",
            cityName = preferences[CITY_NAME_KEY] ?: "Tampico",
            cityRegion = preferences[CITY_REGION_KEY] ?: "Tamaulipas",
            cityCountry = preferences[CITY_COUNTRY_KEY] ?: "México",
            latitude = preferences[LATITUDE_KEY] ?: 22.2331,
            longitude = preferences[LONGITUDE_KEY] ?: -97.8611
        )
    }

    suspend fun saveLanguage(language: String) {
        context.settingsDataStore.edit { preferences ->
            preferences[LANGUAGE_KEY] = language
        }
    }

    suspend fun saveDarkMode(isDarkMode: Boolean) {
        context.settingsDataStore.edit { preferences ->
            preferences[DARK_MODE_KEY] = isDarkMode
        }
    }

    suspend fun saveTemperatureUnit(unit: String) {
        context.settingsDataStore.edit { preferences ->
            preferences[TEMPERATURE_UNIT_KEY] = unit
        }
    }

    suspend fun saveSelectedCity(city: CityResult) {
        context.settingsDataStore.edit { preferences ->
            preferences[CITY_NAME_KEY] = city.name
            preferences[CITY_REGION_KEY] = city.admin1 ?: ""
            preferences[CITY_COUNTRY_KEY] = city.country ?: ""
            preferences[LATITUDE_KEY] = city.latitude
            preferences[LONGITUDE_KEY] = city.longitude
        }
    }
    suspend fun saveSelectedFavoriteCity(city: FavoriteCity) {
        context.settingsDataStore.edit { preferences ->
            preferences[CITY_NAME_KEY] = city.name
            preferences[CITY_REGION_KEY] = city.region
            preferences[CITY_COUNTRY_KEY] = city.country
            preferences[LATITUDE_KEY] = city.latitude
            preferences[LONGITUDE_KEY] = city.longitude
        }
    }
}