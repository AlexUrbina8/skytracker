package com.example.skytrack.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

private val FontColor = Color(0xFF3078D0)
@Preview(showBackground = true)
@Composable

fun Header(){
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
        Text("SkyTracker", fontSize = 30.sp, fontWeight = FontWeight.Black, color = FontColor)
        Row() {
            TextButton(onClick = {}) {
                Icon(imageVector = Icons.Default.Notifications, contentDescription = "Notifications")
            }
            TextButton(onClick = {}) {
                Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu")
            }
        }
    }
}