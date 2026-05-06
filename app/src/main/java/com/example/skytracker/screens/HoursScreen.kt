package com.example.skytracker.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Cloud
import androidx.compose.material.icons.outlined.WaterDrop
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.skytracker.data.AppSettings
import com.example.skytracker.data.SettingsStore
import com.example.skytracker.data.WeatherRepository
import com.example.skytracker.data.WeatherResponse
import kotlin.math.roundToInt

@Composable
fun HoursScreen(
    settingsStore: SettingsStore
) {
    val colors = MaterialTheme.colorScheme
    val settings = settingsStore.settings.collectAsState(initial = AppSettings())
    val repository = remember { WeatherRepository() }

    val isSpanish = settings.value.language == "es"

    var weather by remember { mutableStateOf<WeatherResponse?>(null) }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    LaunchedEffect(
        settings.value.latitude,
        settings.value.longitude,
        settings.value.language
    ) {
        isLoading = true
        errorMessage = ""

        weather = repository.getWeather(
            latitude = settings.value.latitude,
            longitude = settings.value.longitude
        )

        if (weather == null) {
            errorMessage = if (isSpanish) {
                "No se pudo cargar el pronóstico"
            } else {
                "Could not load forecast"
            }
        }

        isLoading = false
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background)
            .padding(20.dp)
    ) {
        Text(
            text = if (isSpanish) "Pronóstico por horas" else "Hourly forecast",
            color = colors.onBackground,
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "${settings.value.cityName}, ${settings.value.cityRegion}",
            color = colors.onSurfaceVariant,
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(22.dp))

        when {
            isLoading -> {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator(color = colors.primary)
                }
            }

            errorMessage.isNotBlank() -> {
                Text(
                    text = errorMessage,
                    color = colors.onSurfaceVariant,
                    fontSize = 14.sp
                )
            }

            else -> {
                val hourly = weather?.hourly

                val forecast = if (hourly != null) {
                    hourly.time.take(24).mapIndexed { index, time ->
                        val hour = if (index == 0) {
                            if (isSpanish) "Ahora" else "Now"
                        } else {
                            time.substringAfter("T").take(5)
                        }

                        val temp = hourly.temperature_2m.getOrNull(index) ?: 0.0
                        val humidity = hourly.relative_humidity_2m?.getOrNull(index)

                        HourItemData(
                            hour = hour,
                            temperature = formatTemperature(temp, settings.value.temperatureUnit),
                            humidity = humidity?.toString() ?: "--"
                        )
                    }
                } else {
                    emptyList()
                }

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(forecast) { item ->
                        HourWeatherCard(
                            hour = item.hour,
                            temperature = item.temperature,
                            humidity = item.humidity,
                            unit = settings.value.temperatureUnit
                        )
                    }
                }
            }
        }
    }
}

data class HourItemData(
    val hour: String,
    val temperature: String,
    val humidity: String
)

@Composable
private fun HourWeatherCard(
    hour: String,
    temperature: String,
    humidity: String,
    unit: String
) {
    val colors = MaterialTheme.colorScheme

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(78.dp)
            .border(1.dp, colors.surfaceVariant, RoundedCornerShape(18.dp)),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = colors.surface)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = hour,
                color = colors.primary,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.width(70.dp)
            )

            Icon(
                imageVector = Icons.Outlined.Cloud,
                contentDescription = null,
                tint = colors.primary,
                modifier = Modifier.size(30.dp)
            )

            Spacer(modifier = Modifier.width(18.dp))

            Text(
                text = "$temperature°$unit",
                color = colors.onSurface,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )

            Icon(
                imageVector = Icons.Outlined.WaterDrop,
                contentDescription = null,
                tint = colors.onSurfaceVariant,
                modifier = Modifier.size(18.dp)
            )

            Spacer(modifier = Modifier.width(4.dp))

            Text(
                text = "$humidity%",
                color = colors.onSurfaceVariant,
                fontSize = 13.sp
            )
        }
    }
}

private fun formatTemperature(
    celsius: Double,
    unit: String
): String {
    return if (unit == "F") {
        ((celsius * 9 / 5) + 32).roundToInt().toString()
    } else {
        celsius.roundToInt().toString()
    }
}