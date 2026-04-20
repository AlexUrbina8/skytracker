package com.example.skytrack.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val CardBackground = Color(0xFF0F172A)
private val MiniCardBackground = Color(0xFF1E293B)
private val PrimaryText = Color(0xFFFFFFFF)
private val SecondaryText = Color(0xB3FFFFFF)
@Preview(showBackground = true)
@Composable
fun HomeWeatherWidget(){
    Box(modifier = Modifier.fillMaxWidth().height(300.dp).background(color = CardBackground, shape = RoundedCornerShape(15.dp)).padding(20.dp)){
        Column(modifier = Modifier.fillMaxHeight(), verticalArrangement = Arrangement.SpaceBetween) {
            Text("TU UBICACIÓN", fontSize = 15.sp, color = SecondaryText)
            Text("Tampico, Tamaulipas", fontSize = 25.sp, color = PrimaryText)
            Text("28°c", fontSize = 70.sp, color = PrimaryText)
            Text("Parcialmente nublado", fontSize = 15.sp, color = SecondaryText)
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                Column(modifier = Modifier.background(Color(0xFF1E293B), shape = RoundedCornerShape(10.dp)).padding(vertical = 10.dp).weight(1f), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Humedad", color = Color(0xB3FFFFFF))
                    Text("72%", color = Color.White)
                }
                Spacer(modifier = Modifier.width(15.dp))
                Column(modifier = Modifier.background(Color(0xFF1E293B), shape = RoundedCornerShape(10.dp)).padding(vertical = 10.dp).weight(1f),verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Viento", color = Color(0xB3FFFFFF))
                    Text("14 km/h", color = Color.White)
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column(modifier = Modifier.background(Color(0xFF1E293B), shape = RoundedCornerShape(10.dp)).padding(vertical = 10.dp).weight(1f),verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Sensación", color = Color(0xB3FFFFFF))
                    Text("31°", color = Color.White)
                }
            }
        }
    }
}