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
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.skytracker.data.AppSettings
import com.example.skytracker.data.FavoriteCity
import com.example.skytracker.data.FavoritesStore
import com.example.skytracker.data.SettingsStore
import kotlinx.coroutines.launch

@Composable
fun FavoritesScreen(
    settingsStore: SettingsStore
) {
    val colors = MaterialTheme.colorScheme
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val settings = settingsStore.settings.collectAsState(initial = AppSettings())
    val isSpanish = settings.value.language == "es"

    val favoritesStore = remember {
        FavoritesStore(context)
    }

    val favorites = favoritesStore.favorites.collectAsState(initial = emptyList())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background)
            .padding(20.dp)
    ) {
        Text(
            text = if (isSpanish) "Favoritos" else "Favorites",
            color = colors.onBackground,
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = if (isSpanish) "Tus ciudades guardadas" else "Your saved cities",
            color = colors.onSurfaceVariant,
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(22.dp))

        if (favorites.value.isEmpty()) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, colors.surfaceVariant, RoundedCornerShape(20.dp)),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = colors.surface)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Star,
                        contentDescription = null,
                        tint = colors.primary
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = if (isSpanish) {
                            "Aún no tienes favoritos"
                        } else {
                            "You do not have favorites yet"
                        },
                        color = colors.onSurface,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = if (isSpanish) {
                            "Busca una ciudad y agrégala a favoritos."
                        } else {
                            "Search for a city and add it to favorites."
                        },
                        color = colors.onSurfaceVariant,
                        fontSize = 13.sp
                    )
                }
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(favorites.value) { city ->
                    FavoriteCityCard(
                        city = city,
                        isSpanish = isSpanish,
                        onSelect = {
                            scope.launch {
                                settingsStore.saveSelectedFavoriteCity(city)
                            }
                        },
                        onDelete = {
                            scope.launch {
                                favoritesStore.removeFavorite(city)
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun FavoriteCityCard(
    city: FavoriteCity,
    isSpanish: Boolean,
    onSelect: () -> Unit,
    onDelete: () -> Unit
) {
    val colors = MaterialTheme.colorScheme

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(92.dp)
            .border(1.dp, colors.surfaceVariant, RoundedCornerShape(20.dp))
            .clickable {
                onSelect()
            },
        shape = RoundedCornerShape(20.dp),
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

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = city.name,
                    color = colors.onSurface,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "${city.region}, ${city.country}",
                    color = colors.onSurfaceVariant,
                    fontSize = 12.sp
                )

                Text(
                    text = if (isSpanish) {
                        "Tocar para usar esta ciudad"
                    } else {
                        "Tap to use this city"
                    },
                    color = colors.primary,
                    fontSize = 12.sp
                )
            }

            IconButton(
                onClick = onDelete
            ) {
                Icon(
                    imageVector = Icons.Outlined.Delete,
                    contentDescription = if (isSpanish) "Eliminar" else "Delete",
                    tint = colors.onSurfaceVariant
                )
            }
        }
    }
}