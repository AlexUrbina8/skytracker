package com.example.skytrack.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.skytrack.components.Footer
import com.example.skytrack.components.Header
import com.example.skytrack.components.HomeWeatherWidget

@Preview(showBackground = true)
@Composable
fun HomePage(){
    Column(modifier = Modifier.fillMaxSize().background(Color(0xFF030A18)).padding(start = 10.dp, end = 10.dp, top = 40.dp)) {
        Header()
        Spacer(modifier = Modifier.height(10.dp))
        HomeWeatherWidget()
        Spacer(modifier = Modifier.weight(1f))
        Footer()
    }
}