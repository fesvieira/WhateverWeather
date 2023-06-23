package com.fesvieira.whateverweather.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.fesvieira.whateverweather.R
import com.fesvieira.whateverweather.components.FormsTextField
import com.fesvieira.whateverweather.helpers.formatLocale
import com.fesvieira.whateverweather.helpers.formatTemperature
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

    val error by remember(currentCityWeather) {
        derivedStateOf {
            currentCityWeather as? Result.Error
        }
    }
    var textState by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(""))
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBlue)
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        AnimatedVisibility(
            visible = weatherData != null,
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier.align(Alignment.TopCenter)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = weatherData?.location?.formatLocale ?: "",
                    style = Typography.bodyMedium,
                    textAlign = TextAlign.Center
                )

                Text(
                    text = weatherData?.weather?.temp_c?.formatTemperature ?: "",
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

        AnimatedVisibility(
            visible = weatherData == null,
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier.align(Alignment.TopCenter)
        ) {
            Text(
                text = if (error == null) "Type a location to get weather" else "Location not found...",
                style = Typography.bodyLarge,
                color = Gray,
                textAlign = TextAlign.Center
            )
        }

        FormsTextField(
            textState = textState,
            onValueChange = {
                textState = it
            },
            trailingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_search),
                    contentDescription = null,
                    tint = Color.DarkGray,
                    modifier = Modifier
                        .clip(CircleShape)
                        .clickable {
                            weatherViewModel.getWeather(textState.text)
                        }
                        .padding(12.dp)
                )
            },
            placeholder = "Discover weather...",
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        )
    }
}