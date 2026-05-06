package com.example.skytracker.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.favoritesDataStore by preferencesDataStore(name = "favorites")

data class FavoriteCity(
    val name: String,
    val region: String,
    val country: String,
    val latitude: Double,
    val longitude: Double
)

class FavoritesStore(private val context: Context) {

    companion object {
        private val FAVORITES_KEY = stringSetPreferencesKey("favorite_cities")
    }

    val favorites: Flow<List<FavoriteCity>> = context.favoritesDataStore.data.map { preferences ->
        val savedSet = preferences[FAVORITES_KEY] ?: emptySet()

        savedSet.mapNotNull { item ->
            item.toFavoriteCityOrNull()
        }.sortedBy { it.name }
    }

    suspend fun addFavorite(city: CityResult) {
        context.favoritesDataStore.edit { preferences ->
            val currentFavorites = preferences[FAVORITES_KEY] ?: emptySet()
            val newFavorite = city.toStorageString()

            preferences[FAVORITES_KEY] = currentFavorites + newFavorite
        }
    }

    suspend fun removeFavorite(city: FavoriteCity) {
        context.favoritesDataStore.edit { preferences ->
            val currentFavorites = preferences[FAVORITES_KEY] ?: emptySet()

            preferences[FAVORITES_KEY] = currentFavorites.filterNot { item ->
                val savedCity = item.toFavoriteCityOrNull()

                savedCity?.name == city.name &&
                        savedCity.region == city.region &&
                        savedCity.country == city.country
            }.toSet()
        }
    }

    private fun CityResult.toStorageString(): String {
        val safeRegion = admin1 ?: ""
        val safeCountry = country ?: ""

        return "$name|$safeRegion|$safeCountry|$latitude|$longitude"
    }

    private fun String.toFavoriteCityOrNull(): FavoriteCity? {
        return try {
            val parts = split("|")

            FavoriteCity(
                name = parts[0],
                region = parts[1],
                country = parts[2],
                latitude = parts[3].toDouble(),
                longitude = parts[4].toDouble()
            )
        } catch (e: Exception) {
            null
        }
    }
}