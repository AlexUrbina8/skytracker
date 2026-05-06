package com.example.skytracker.data

class WeatherRepository {

    private val geocodingApi = ApiClient.geocodingApi
    private val weatherApi = ApiClient.weatherApi

    suspend fun searchCity(query: String): List<CityResult> {
        if (query.isBlank()) return emptyList()

        return try {
            geocodingApi.searchCity(name = query).results ?: emptyList()
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun getWeather(latitude: Double, longitude: Double): WeatherResponse? {
        return try {
            weatherApi.getForecast(
                latitude = latitude,
                longitude = longitude
            )
        } catch (e: Exception) {
            null
        }
    }

    fun getWeatherDescription(code: Int, language: String): String {
        val isSpanish = language == "es"

        return when (code) {
            0 -> if (isSpanish) "Despejado" else "Clear sky"
            1, 2 -> if (isSpanish) "Parcialmente nublado" else "Partly cloudy"
            3 -> if (isSpanish) "Nublado" else "Cloudy"
            45, 48 -> if (isSpanish) "Niebla" else "Fog"
            51, 53, 55 -> if (isSpanish) "Llovizna" else "Drizzle"
            61, 63, 65 -> if (isSpanish) "Lluvia" else "Rain"
            71, 73, 75 -> if (isSpanish) "Nieve" else "Snow"
            80, 81, 82 -> if (isSpanish) "Chubascos" else "Showers"
            95 -> if (isSpanish) "Tormenta" else "Thunderstorm"
            96, 99 -> if (isSpanish) "Tormenta con granizo" else "Thunderstorm with hail"
            else -> if (isSpanish) "Clima variable" else "Variable weather"
        }
    }
}