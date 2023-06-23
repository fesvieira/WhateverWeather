package com.fesvieira.whateverweather.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.fesvieira.whateverweather.R
import com.fesvieira.whateverweather.components.FormsTextField
import com.fesvieira.whateverweather.helpers.formatLocale
import com.fesvieira.whateverweather.helpers.formatTemperature
import com.fesvieira.whateverweather.helpers.gradientBackground
import com.fesvieira.whateverweather.helpers.weatherGradient
import com.fesvieira.whateverweather.helpers.weatherLottie
import com.fesvieira.whateverweather.helpers.withShadow
import com.fesvieira.whateverweather.models.Result
import com.fesvieira.whateverweather.ui.theme.Gray
import com.fesvieira.whateverweather.ui.theme.MidnightBlue
import com.fesvieira.whateverweather.ui.theme.Typography
import com.fesvieira.whateverweather.viewmodels.WeatherViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun StartScreen(
    navController: NavHostController? = null,
    weatherViewModel: WeatherViewModel = viewModel()
) {
    val systemUiController = rememberSystemUiController()
    val currentCityWeather by weatherViewModel.currentCityWeather.collectAsState()

    val weatherData by remember(currentCityWeather) {
        derivedStateOf { (currentCityWeather as? Result.Success)?.data }
    }

    val error by remember(currentCityWeather) {
        derivedStateOf { currentCityWeather as? Result.Error }
    }
    var textState by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(""))
    }

    val lottieRes by remember(weatherData) { derivedStateOf { weatherData.weatherLottie } }
    val weatherGradient by remember(weatherData) { derivedStateOf { weatherData.weatherGradient } }
    var isPlayingAnimation by remember { mutableStateOf(true) }

    val composition by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(lottieRes ?: R.raw.sunny)
    )
    val logoAnimationState =
        animateLottieCompositionAsState(
            composition = composition,
            iterations = LottieConstants.IterateForever,
            isPlaying = isPlayingAnimation,
            speed = if (lottieRes == R.raw.sunny) 0.5f else 2.0f
        )

    val focusRequester = remember {
        FocusRequester()
    }

    val focusManager = LocalFocusManager.current

    LaunchedEffect(weatherData) {
        systemUiController.setStatusBarColor(
            weatherGradient[0],
            darkIcons = lottieRes == R.raw.clear_night
        )
        systemUiController.setNavigationBarColor(
            weatherGradient[1],
            darkIcons = lottieRes == R.raw.clear_night
        )
        isPlayingAnimation = true
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .gradientBackground(colors = weatherGradient, angle = 270f)
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
                LottieAnimation(
                    composition = composition,
                    progress = {
                        logoAnimationState.progress
                    },
                    modifier = Modifier
                        .size(180.dp)
                )

                Text(
                    text = weatherData?.location?.formatLocale ?: "",
                    style = Typography.bodyMedium.withShadow,
                    textAlign = TextAlign.Center,
                )

                Text(
                    text = weatherData?.weather?.temp_c?.formatTemperature ?: "",
                    style = Typography.headlineLarge.withShadow,
                )

                Text(
                    text = weatherData?.weather?.condition?.text ?: "",
                    style = Typography.bodyMedium.withShadow,
                    color = Color.White,
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
            backgroundColor = MidnightBlue.copy(alpha = 0.6f),
            focusRequester = focusRequester,
            onValueChange = {
                textState = it
            },
            trailingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_search),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier
                        .clip(CircleShape)
                        .clickable {
                            focusManager.clearFocus()
                            weatherViewModel.getWeather(textState.text)
                        }
                        .padding(12.dp)
                )
            },
            placeholder = "Discover weather...",
            onDone = {
                 focusManager.clearFocus()
                 weatherViewModel.getWeather(textState.text)
            },
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .onFocusChanged {
                    if (it.hasFocus) textState = TextFieldValue("")
                }
        )
    }
}