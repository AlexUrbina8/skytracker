package com.example.skytracker.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.skytracker.components.NavBar
import com.example.skytracker.data.AppSettings
import com.example.skytracker.data.SettingsStore
import com.example.skytracker.screens.FavoritesScreen
import com.example.skytracker.screens.HomeScreen
import com.example.skytracker.screens.HoursScreen
import com.example.skytracker.screens.SearchScreen
import com.example.skytracker.screens.SettingsScreen
import com.example.skytracker.screens.WeekScreen

@Composable
fun AppNavigation(
    settingsStore: SettingsStore
) {
    val navController = rememberNavController()

    val settings = settingsStore.settings.collectAsState(initial = AppSettings())
    val isSpanish = settings.value.language == "es"

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            NavBar(
                navController = navController,
                currentRoute = currentRoute,
                isSpanish = isSpanish
            )
        }
    ) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = Routes.HOME,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Routes.HOME) {
                HomeScreen(settingsStore = settingsStore)
            }

            composable(Routes.SEARCH) {
                SearchScreen(settingsStore = settingsStore)
            }

            composable(Routes.HOURS) {
                HoursScreen(settingsStore = settingsStore)
            }

            composable(Routes.WEEK) {
                WeekScreen(settingsStore = settingsStore)
            }

            composable(Routes.FAVORITES) {
                FavoritesScreen(settingsStore = settingsStore)
            }

            composable(Routes.SETTINGS) {
                SettingsScreen(settingsStore = settingsStore)
            }
        }
    }
}