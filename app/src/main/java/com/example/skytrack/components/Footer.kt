package com.example.skytrack.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

private val IconColor = Color(0xFF4694F8)

@Preview(showBackground = true)
@Composable
fun Footer(){
    Row(modifier = Modifier.fillMaxWidth().padding(bottom = 5.dp), horizontalArrangement = Arrangement.SpaceBetween) {
        TextButton(onClick = {}) {
            Icon(imageVector = Icons.Default.Home, contentDescription = "Home", tint = IconColor)
        }
        TextButton(onClick = {}) {
            Icon(imageVector = Icons.Default.Search, contentDescription = "Search", tint = IconColor)
        }
        TextButton(onClick = {}) {
            Icon(imageVector = Icons.Default.Home, contentDescription = "Hours", tint = IconColor)
        }
        TextButton(onClick = {}) {
            Icon(imageVector = Icons.Default.DateRange, contentDescription = "Week", tint = IconColor)
        }
        TextButton(onClick = {}) {
            Icon(imageVector = Icons.Default.FavoriteBorder, contentDescription = "Favorites", tint = IconColor)
        }
        TextButton(onClick = {}) {
            Icon(imageVector = Icons.Default.Settings, contentDescription = "Settings", tint = IconColor)
        }
    }
}