package com.example.skytrack.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview(showBackground = true)
@Composable
fun SearchWeatherWidget(){
    Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp, vertical = 20.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
        Column() {
            Text("Ciudad de México", fontSize = 20.sp)
            Spacer(modifier = Modifier.height(5.dp))
            Text("México, UTC-6", fontSize = 15.sp)
        }
        Column() {
            Text("22°", fontSize = 25.sp)
        }
    }
}