package com.example.skytrack.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.skytrack.components.Footer
import com.example.skytrack.components.Header
import com.example.skytrack.components.SearchWeatherWidget

@Preview(showBackground = true)
@Composable
fun SearchPage(){
    Column(modifier = Modifier.fillMaxSize().padding(start = 10.dp, end = 10.dp, top = 40.dp)) {
        Header()
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