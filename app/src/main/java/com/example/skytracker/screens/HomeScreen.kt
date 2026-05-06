package com.example.skytracker.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Air
import androidx.compose.material.icons.outlined.Cloud
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Thermostat
import androidx.compose.material.icons.outlined.WaterDrop
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.skytracker.data.AppSettings
import com.example.skytracker.data.SettingsStore
import com.example.skytracker.data.WeatherRepository
import com.example.skytracker.data.WeatherResponse
import kotlin.math.roundToInt

@Composable
fun HomeScreen(
    settingsStore: SettingsStore
) {
    val colors = MaterialTheme.colorScheme
    val settings = settingsStore.settings.collectAsState(initial = AppSettings())
    val isSpanish = settings.value.language == "es"

    val repository = remember { WeatherRepository() }

    var weather by remember { mutableStateOf<WeatherResponse?>(null) }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    LaunchedEffect(settings.value.latitude, settings.value.longitude, settings.value.language) {
        isLoading = true
        errorMessage = ""

        weather = repository.getWeather(
            latitude = settings.value.latitude,
            longitude = settings.value.longitude
        )

        if (weather == null) {
            errorMessage = if (isSpanish) {
                "No se pudo cargar el clima"
            } else {
                "Could not load weather"
            }
        }

        isLoading = false
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background)
            .padding(horizontal = 20.dp, vertical = 18.dp)
    ) {
        HomeHeader()

        Spacer(modifier = Modifier.height(20.dp))

        when {
            isLoading -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(365.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = colors.primary
                    )
                }
            }

            errorMessage.isNotBlank() -> {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = colors.surface)
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = errorMessage,
                            color = colors.onSurface
                        )
                    }
                }
            }

            else -> {
                CurrentWeatherCard(
                    cityName = settings.value.cityName,
                    cityRegion = settings.value.cityRegion,
                    temperatureUnit = settings.value.temperatureUnit,
                    language = settings.value.language,
                    weather = weather,
                    repository = repository
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = if (isSpanish) "PRÓXIMAS HORAS" else "NEXT HOURS",
            color = colors.onSurfaceVariant,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.5.sp
        )

        Spacer(modifier = Modifier.height(12.dp))

        HourlyPreview(
            weather = weather,
            temperatureUnit = settings.value.temperatureUnit,
            language = settings.value.language
        )
    }
}

@Composable
private fun HomeHeader() {
    val colors = MaterialTheme.colorScheme

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "SkyTrack",
            color = colors.primary,
            fontSize = 28.sp,
            fontWeight = FontWeight.ExtraBold
        )

        IconButton(
            onClick = {},
            modifier = Modifier
                .size(42.dp)
                .clip(RoundedCornerShape(14.dp))
                .background(colors.surface)
                .border(1.dp, colors.surfaceVariant, RoundedCornerShape(14.dp))
        ) {
            Icon(
                imageVector = Icons.Outlined.Notifications,
                contentDescription = null,
                tint = colors.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun CurrentWeatherCard(
    cityName: String,
    cityRegion: String,
    temperatureUnit: String,
    language: String,
    weather: WeatherResponse?,
    repository: WeatherRepository
) {
    val colors = MaterialTheme.colorScheme
    val isSpanish = language == "es"

    val current = weather?.current_weather
    val hourly = weather?.hourly

    val temp = current?.temperature ?: 0.0
    val wind = current?.windspeed ?: 0.0
    val code = current?.weathercode ?: 0

    val humidity = hourly?.relative_humidity_2m?.firstOrNull()
    val apparentTemperature = hourly?.apparent_temperature?.firstOrNull() ?: temp

    val displayedTemp = formatTemperature(temp, temperatureUnit)
    val displayedFeelsLike = formatTemperature(apparentTemperature, temperatureUnit)

    val description = repository.getWeatherDescription(
        code = code,
        language = language
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(365.dp)
            .border(1.dp, colors.surfaceVariant, RoundedCornerShape(24.dp)),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = colors.surface)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(
                            colors.surface,
                            colors.surfaceVariant
                        )
                    )
                )
                .padding(24.dp)
        ) {
            Column {
                Text(
                    text = if (isSpanish) "TU UBICACIÓN" else "YOUR LOCATION",
                    color = colors.onSurfaceVariant,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Outlined.LocationOn,
                        contentDescription = null,
                        tint = colors.primary,
                        modifier = Modifier.size(18.dp)
                    )

                    Spacer(modifier = Modifier.width(6.dp))

                    Text(
                        text = "$cityName,\n$cityRegion",
                        color = colors.onSurface,
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold,
                        lineHeight = 28.sp
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = displayedTemp,
                        color = colors.onSurface,
                        fontSize = 68.sp,
                        fontWeight = FontWeight.ExtraBold
                    )

                    Text(
                        text = "°$temperatureUnit",
                        color = colors.onSurfaceVariant,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 12.dp)
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    Icon(
                        imageVector = Icons.Outlined.Cloud,
                        contentDescription = null,
                        tint = colors.primary,
                        modifier = Modifier.size(52.dp)
                    )
                }

                Text(
                    text = description,
                    color = colors.onSurfaceVariant,
                    fontSize = 14.sp
                )

                Spacer(modifier = Modifier.height(14.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    WeatherMiniCard(
                        title = if (isSpanish) "Humedad" else "Humidity",
                        value = if (humidity != null) "$humidity%" else "--",
                        icon = Icons.Outlined.WaterDrop,
                        modifier = Modifier.weight(1f)
                    )

                    WeatherMiniCard(
                        title = if (isSpanish) "Viento" else "Wind",
                        value = "${wind.roundToInt()} km/h",
                        icon = Icons.Outlined.Air,
                        modifier = Modifier.weight(1f)
                    )

                    WeatherMiniCard(
                        title = if (isSpanish) "Sensación" else "Feels like",
                        value = "$displayedFeelsLike°",
                        icon = Icons.Outlined.Thermostat,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Composable
private fun WeatherMiniCard(
    title: String,
    value: String,
    icon: ImageVector,
    modifier: Modifier = Modifier
) {
    val colors = MaterialTheme.colorScheme

    Card(
        modifier = modifier.height(82.dp),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = colors.background.copy(alpha = 0.35f)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = colors.onSurfaceVariant,
                modifier = Modifier.size(16.dp)
            )

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = title,
                color = colors.onSurfaceVariant,
                fontSize = 9.sp,
                maxLines = 1
            )

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = value,
                color = colors.primary,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1
            )
        }
    }
}

@Composable
private fun HourlyPreview(
    weather: WeatherResponse?,
    temperatureUnit: String,
    language: String
) {
    val colors = MaterialTheme.colorScheme
    val isSpanish = language == "es"

    val hourly = weather?.hourly

    val hours = if (hourly != null) {
        hourly.time.take(8).mapIndexed { index, time ->
            val temp = hourly.temperature_2m.getOrNull(index) ?: 0.0
            val hourText = if (index == 0) {
                if (isSpanish) "Ahora" else "Now"
            } else {
                time.substringAfter("T").take(5)
            }

            hourText to formatTemperature(temp, temperatureUnit)
        }
    } else {
        emptyList()
    }

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(hours) { item ->
            val selected = item.first == "Ahora" || item.first == "Now"

            Card(
                modifier = Modifier
                    .width(64.dp)
                    .height(92.dp)
                    .border(
                        width = if (selected) 1.dp else 0.dp,
                        color = if (selected) colors.primary else colors.surface,
                        shape = RoundedCornerShape(20.dp)
                    ),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (selected) colors.surfaceVariant else colors.surface
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(vertical = 10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = item.first,
                        color = if (selected) colors.primary else colors.onSurfaceVariant,
                        fontSize = 11.sp
                    )

                    Icon(
                        imageVector = Icons.Outlined.Cloud,
                        contentDescription = null,
                        tint = colors.primary,
                        modifier = Modifier.size(24.dp)
                    )

                    Text(
                        text = "${item.second}°",
                        color = colors.onSurface,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
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