package com.example.skytracker.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.example.skytracker.navigation.Routes

data class NavBarItem(
    val route: String,
    val label: String,
    val icon: ImageVector
)

@Composable
fun NavBar(
    navController: NavController,
    currentRoute: String?,
    isSpanish: Boolean
) {
    val colors = MaterialTheme.colorScheme

    val items = listOf(
        NavBarItem(
            route = Routes.HOME,
            label = if (isSpanish) "Inicio" else "Home",
            icon = Icons.Outlined.Home
        ),
        NavBarItem(
            route = Routes.SEARCH,
            label = if (isSpanish) "Buscar" else "Search",
            icon = Icons.Outlined.Search
        ),
        NavBarItem(
            route = Routes.HOURS,
            label = if (isSpanish) "Horas" else "Hours",
            icon = Icons.Outlined.Schedule
        ),
        NavBarItem(
            route = Routes.WEEK,
            label = if (isSpanish) "Semana" else "Week",
            icon = Icons.Outlined.CalendarMonth
        ),
        NavBarItem(
            route = Routes.FAVORITES,
            label = if (isSpanish) "Favoritos" else "Favorites",
            icon = Icons.Outlined.Star
        ),
        NavBarItem(
            route = Routes.SETTINGS,
            label = if (isSpanish) "Ajustes" else "Settings",
            icon = Icons.Outlined.Settings
        )
    )

    NavigationBar(
        modifier = Modifier.height(72.dp),
        containerColor = colors.background
    ) {
        items.forEach { item ->
            val selected = currentRoute == item.route

            NavigationBarItem(
                selected = selected,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }

                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.label,
                        modifier = Modifier.size(22.dp)
                    )
                },
                label = {
                    Text(
                        text = item.label,
                        fontSize = 10.sp
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = colors.primary,
                    selectedTextColor = colors.primary,
                    unselectedIconColor = colors.onSurfaceVariant,
                    unselectedTextColor = colors.onSurfaceVariant,
                    indicatorColor = colors.surfaceVariant
                )
            )
        }
    }
}