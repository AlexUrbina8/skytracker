package com.example.skytrack.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.skytrack.components.Footer
import com.example.skytrack.components.Header
import com.example.skytrack.components.SearchWeatherWidget

private val CardBackground = Color(0xFF0F172A)
@Preview(showBackground = true)
@Composable
fun SearchPage(){
    val searchbar by remember { mutableStateOf("") }
    Column(modifier = Modifier.fillMaxSize().background(color = CardBackground).padding(start = 10.dp, end = 10.dp, top = 40.dp)) {
        Header()
        Spacer(modifier = Modifier.height(10.dp))
        TextField(value = searchbar, onValueChange = {}, label = { Text("Buscar") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(20.dp))
        SearchWeatherWidget()
        SearchWeatherWidget()
        SearchWeatherWidget()
        SearchWeatherWidget()
        SearchWeatherWidget()
        SearchWeatherWidget()
        Spacer(modifier = Modifier.weight(1f))
        Footer()
    }
}