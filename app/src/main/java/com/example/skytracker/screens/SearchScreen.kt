package com.example.skytracker.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.skytracker.data.AppSettings
import com.example.skytracker.data.CityResult
import com.example.skytracker.data.FavoritesStore
import com.example.skytracker.data.SettingsStore
import com.example.skytracker.data.WeatherRepository
import kotlinx.coroutines.launch

@Composable
fun SearchScreen(settingsStore: SettingsStore) {
    val colors = MaterialTheme.colorScheme
    val scope = rememberCoroutineScope()
    val repository = remember { WeatherRepository() }

    val settings = settingsStore.settings.collectAsState(initial = AppSettings())
    val isSpanish = settings.value.language == "es"

    val context = LocalContext.current
    val favoritesStore = remember {
        FavoritesStore(context)
    }

    var query by remember { mutableStateOf("") }
    var cities by remember { mutableStateOf<List<CityResult>>(emptyList()) }
    var isLoading by remember { mutableStateOf(false) }
    var selectedCity by remember { mutableStateOf<CityResult?>(null) }
    var message by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background)
            .padding(20.dp)
    ) {
        Text(
            text = if (isSpanish) "Buscar ciudad" else "Search city",
            color = colors.onBackground,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(18.dp))

        OutlinedTextField(
            value = query,
            onValueChange = { query = it },
            placeholder = {
                Text(
                    text = if (isSpanish) "Escribe una ciudad" else "Type a city",
                    color = colors.onSurfaceVariant
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Outlined.Search,
                    contentDescription = null,
                    tint = colors.primary
                )
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = colors.onSurface,
                unfocusedTextColor = colors.onSurface,
                focusedBorderColor = colors.primary,
                unfocusedBorderColor = colors.surfaceVariant,
                focusedContainerColor = colors.surface,
                unfocusedContainerColor = colors.surface,
                cursorColor = colors.primary
            )
        )

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = {
                scope.launch {
                    isLoading = true
                    message = ""
                    selectedCity = null

                    cities = repository.searchCity(query)

                    if (cities.isEmpty()) {
                        message = if (isSpanish) {
                            "No se encontraron resultados"
                        } else {
                            "No results found"
                        }
                    }

                    isLoading = false
                }
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(
                text = if (isLoading) {
                    if (isSpanish) "Buscando..." else "Searching..."
                } else {
                    if (isSpanish) "Buscar" else "Search"
                }
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        selectedCity?.let { city ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, colors.primary, RoundedCornerShape(18.dp)),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = colors.surfaceVariant)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = if (isSpanish) "Ciudad seleccionada" else "Selected city",
                        color = colors.primary,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = city.name,
                        color = colors.onSurface,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "${city.admin1 ?: ""}, ${city.country ?: ""}",
                        color = colors.onSurfaceVariant,
                        fontSize = 13.sp
                    )

                    Text(
                        text = "Lat: ${city.latitude} | Lon: ${city.longitude}",
                        color = colors.onSurfaceVariant,
                        fontSize = 12.sp
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedButton(
                        onClick = {
                            scope.launch {
                                favoritesStore.addFavorite(city)
                                message = if (isSpanish) {
                                    "Ciudad agregada a favoritos"
                                } else {
                                    "City added to favorites"
                                }
                            }
                        },
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = if (isSpanish) "Agregar a favoritos" else "Add to favorites"
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
        }

        Text(
            text = if (isSpanish) "RESULTADOS" else "RESULTS",
            color = colors.onSurfaceVariant,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.4.sp
        )

        Spacer(modifier = Modifier.height(12.dp))

        if (message.isNotBlank()) {
            Text(
                text = message,
                color = colors.onSurfaceVariant,
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(8.dp))
        }

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(cities) { city ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(82.dp)
                        .border(1.dp, colors.surfaceVariant, RoundedCornerShape(18.dp))
                        .clickable {
                            selectedCity = city
                            scope.launch {
                                settingsStore.saveSelectedCity(city)
                                message = if (isSpanish) {
                                    "Ciudad guardada correctamente"
                                } else {
                                    "City saved successfully"
                                }
                            }
                        },
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = colors.surface)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 18.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.LocationOn,
                            contentDescription = null,
                            tint = colors.primary
                        )

                        Spacer(modifier = Modifier.width(14.dp))

                        Column {
                            Text(
                                text = city.name,
                                color = colors.onSurface,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )

                            Text(
                                text = "${city.admin1 ?: if (isSpanish) "Sin región" else "No region"}, ${city.country ?: if (isSpanish) "Sin país" else "No country"}",
                                color = colors.onSurfaceVariant,
                                fontSize = 12.sp
                            )

                            Text(
                                text = if (isSpanish) {
                                    "Tocar para seleccionar"
                                } else {
                                    "Tap to select"
                                },
                                color = colors.primary,
                                fontSize = 12.sp
                            )
                        }
                    }
                }
            }
        }
    }
}