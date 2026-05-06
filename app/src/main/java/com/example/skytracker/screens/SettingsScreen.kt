package com.example.skytracker.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SelectableChipColors
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.skytracker.data.AppSettings
import com.example.skytracker.data.SettingsStore
import kotlinx.coroutines.launch

@Composable
fun SettingsScreen(
    settingsStore: SettingsStore
) {
    val colors = MaterialTheme.colorScheme
    val settings = settingsStore.settings.collectAsState(initial = AppSettings())
    val scope = rememberCoroutineScope()

    val isSpanish = settings.value.language == "es"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background)
            .padding(20.dp)
    ) {
        Text(
            text = if (isSpanish) "Ajustes" else "Settings",
            color = colors.onBackground,
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = if (isSpanish) "Personaliza SkyTrack" else "Customize SkyTrack",
            color = colors.onSurfaceVariant,
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(24.dp))

        SettingsCard(title = if (isSpanish) "Idioma" else "Language") {
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                FilterChip(
                    selected = settings.value.language == "es",
                    onClick = {
                        scope.launch {
                            settingsStore.saveLanguage("es")
                        }
                    },
                    label = { Text("Español") },
                    colors = chipColors()
                )

                FilterChip(
                    selected = settings.value.language == "en",
                    onClick = {
                        scope.launch {
                            settingsStore.saveLanguage("en")
                        }
                    },
                    label = { Text("English") },
                    colors = chipColors()
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        SettingsCard(title = if (isSpanish) "Apariencia" else "Appearance") {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = if (isSpanish) "Modo oscuro" else "Dark mode",
                    color = colors.onSurface,
                    fontSize = 15.sp
                )

                Switch(
                    checked = settings.value.isDarkMode,
                    onCheckedChange = { checked ->
                        scope.launch {
                            settingsStore.saveDarkMode(checked)
                        }
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        SettingsCard(title = if (isSpanish) "Unidades" else "Units") {
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                FilterChip(
                    selected = settings.value.temperatureUnit == "C",
                    onClick = {
                        scope.launch {
                            settingsStore.saveTemperatureUnit("C")
                        }
                    },
                    label = { Text("°C") },
                    colors = chipColors()
                )

                FilterChip(
                    selected = settings.value.temperatureUnit == "F",
                    onClick = {
                        scope.launch {
                            settingsStore.saveTemperatureUnit("F")
                        }
                    },
                    label = { Text("°F") },
                    colors = chipColors()
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        AppInfoCard(
            isSpanish = isSpanish
        )
    }
}

@Composable
private fun AppInfoCard(
    isSpanish: Boolean
) {
    val colors = MaterialTheme.colorScheme

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, colors.surfaceVariant, RoundedCornerShape(20.dp)),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = colors.surface)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier
                    .size(42.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(colors.surfaceVariant),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Outlined.Info,
                    contentDescription = null,
                    tint = colors.primary,
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(modifier = Modifier.size(14.dp))

            Column {
                Text(
                    text = "SkyTrack v1.0",
                    color = colors.onSurface,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = if (isSpanish) {
                        "IEST · Programación Móvil I"
                    } else {
                        "IEST · Mobile Programming I"
                    },
                    color = colors.onSurfaceVariant,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Composable
private fun SettingsCard(
    title: String,
    content: @Composable ColumnScope.() -> Unit
) {
    val colors = MaterialTheme.colorScheme

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, colors.surfaceVariant, RoundedCornerShape(20.dp)),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = colors.surface)
    ) {
        Column(
            modifier = Modifier.padding(18.dp)
        ) {
            Text(
                text = title,
                color = colors.onSurfaceVariant,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.2.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            content()
        }
    }
}

@Composable
private fun chipColors(): SelectableChipColors {
    val colors = MaterialTheme.colorScheme

    return FilterChipDefaults.filterChipColors(
        selectedContainerColor = colors.primary,
        selectedLabelColor = colors.onPrimary,
        containerColor = colors.surfaceVariant,
        labelColor = colors.onSurface
    )
}