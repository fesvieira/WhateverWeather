package com.fesvieira.whateverweather.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.fesvieira.whateverweather.components.FormsTextField
import com.fesvieira.whateverweather.models.Result
import com.fesvieira.whateverweather.ui.theme.DarkBlue
import com.fesvieira.whateverweather.ui.theme.Gray
import com.fesvieira.whateverweather.ui.theme.Typography
import com.fesvieira.whateverweather.viewmodels.WeatherViewModel

@Composable
fun StartScreen(
    navController: NavHostController? = null,
    weatherViewModel: WeatherViewModel = viewModel()
) {
    val currentCityWeather by weatherViewModel.currentCityWeather.collectAsState()

    val weatherData by remember(currentCityWeather) {
        derivedStateOf {
            (currentCityWeather as? Result.Success)?.data
        }
    }

    LaunchedEffect(key1 = Unit) {
        weatherViewModel.getWeather("New York")
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBlue)
    ) {
        FormsTextField(
            textState = TextFieldValue(),
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        )
        if (weatherData != null) {
            Text(
                text = weatherData?.location?.name ?: "",
                style = Typography.bodyLarge,
                modifier = Modifier.padding(top = 16.dp)
            )

            Text(
                text = (weatherData?.weather?.temp_c ?: 0).toString(),
                style = Typography.headlineLarge
            )

            Text(
                text = weatherData?.weather?.condition?.text ?: "",
                style = Typography.bodyMedium,
                color = Gray,
                fontWeight = FontWeight.ExtraBold
            )
        }
    }
}