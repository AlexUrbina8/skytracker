package com.example.skytracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import com.example.skytracker.data.AppSettings
import com.example.skytracker.data.SettingsStore
import com.example.skytracker.navigation.AppNavigation
import com.example.skytracker.ui.theme.SkyTrackerTheme

class MainActivity : ComponentActivity() {

    private lateinit var settingsStore: SettingsStore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        settingsStore = SettingsStore(applicationContext)

        setContent {
            val settings = settingsStore.settings.collectAsState(
                initial = AppSettings()
            )

            SkyTrackerTheme(
                darkTheme = settings.value.isDarkMode
            ) {
                AppNavigation(
                    settingsStore = settingsStore
                )
            }
        }
    }
}