package com.example.skytrack.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val IconColor = Color(0xFF4694F8)
private val MiniCardBackground = Color(0xFF1E293B)
private val PrimaryText = Color(0xFFFFFFFF)
private val SecondaryText = Color(0xB3FFFFFF)
@Preview(showBackground = true)
@Composable
fun WeekWeatheWidget(){
    Row(modifier = Modifier.fillMaxWidth().background(color = MiniCardBackground, shape = RoundedCornerShape(15.dp)).padding(vertical = 20.dp, horizontal = 15.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
        Column() {
            Text("Hoy", fontSize = 18.sp, color = IconColor, fontWeight = FontWeight.Black)
        }
        Column() {
            Text("Parcialmente Nublado", color = SecondaryText, fontSize = 14.sp)
        }
        Column() {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                Text("21°", fontSize = 20.sp, color = SecondaryText)
                Spacer(modifier = Modifier.width(5.dp))
                Text("28°", fontSize = 22.sp, fontWeight = FontWeight.Black, color = IconColor)
            }
        }

    }
    Spacer(modifier = Modifier.height(5.dp))
}