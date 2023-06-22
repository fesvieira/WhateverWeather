package com.fesvieira.whateverweather.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.fesvieira.whateverweather.ui.theme.DarkBlue
import com.fesvieira.whateverweather.ui.theme.Gray
import com.fesvieira.whateverweather.ui.theme.Typography
import com.fesvieira.whateverweather.viewmodels.WeatherViewModel

@Composable
fun StartScreen(
    navController: NavHostController? = null,
    weatherViewModel: WeatherViewModel? = null
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBlue)
    ) {
        Text(
            text = "City name",
            style = Typography.bodyLarge,
            modifier = Modifier.padding(top = 50.dp)
        )

        Text(
            text = "25º",
            style = Typography.headlineLarge
        )

        Text(
            text = "City name",
            style = Typography.bodyMedium,
            color = Gray,
            fontWeight = FontWeight.ExtraBold
        )
    }
}

@Preview
@Composable
fun Preview() {
    StartScreen()
}