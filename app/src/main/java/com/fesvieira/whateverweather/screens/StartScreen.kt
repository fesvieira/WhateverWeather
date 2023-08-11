package com.fesvieira.whateverweather.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
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
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.FocusState
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
import com.fesvieira.whateverweather.components.SearchTextField
import com.fesvieira.whateverweather.helpers.formatLocale
import com.fesvieira.whateverweather.helpers.formatTemperature
import com.fesvieira.whateverweather.helpers.gradientBackground
import com.fesvieira.whateverweather.helpers.weatherGradient
import com.fesvieira.whateverweather.helpers.weatherLottie
import com.fesvieira.whateverweather.helpers.withShadow
import com.fesvieira.whateverweather.models.Result
import com.fesvieira.whateverweather.ui.theme.Gray
import com.fesvieira.whateverweather.ui.theme.MidnightBlue
import com.fesvieira.whateverweather.ui.theme.TextFieldBackground
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

    val isLoading by remember(currentCityWeather) {
        derivedStateOf { currentCityWeather as? Result.Loading != null }
    }

    val lottieRes by remember(weatherData) { derivedStateOf { weatherData.weatherLottie } }
    val weatherGradient by remember(weatherData) { derivedStateOf { weatherData.weatherGradient } }
    var isPlayingAnimation by remember { mutableStateOf(true) }

    val composition by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(lottieRes ?: R.raw.loading)
    )
    val logoAnimationState =
        animateLottieCompositionAsState(
            composition = composition,
            iterations = LottieConstants.IterateForever,
            isPlaying = isPlayingAnimation,
            speed = if (lottieRes == R.raw.sunny) 0.5f else 2.0f
        )

    val loadingComposition by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(R.raw.loading)
    )
    val loadingAnimationState =
        animateLottieCompositionAsState(
            composition = loadingComposition,
            iterations = LottieConstants.IterateForever
        )

    val focusRequester = remember {
        FocusRequester()
    }

    val focusManager = LocalFocusManager.current

    LaunchedEffect(weatherData) {
        systemUiController.setStatusBarColor(weatherGradient[0])
        systemUiController.setNavigationBarColor(weatherGradient[1])
        isPlayingAnimation = true
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .gradientBackground(colors = weatherGradient, angle = 270f)
            .padding(16.dp)
    ) {
        AnimatedVisibility(
            visible = isLoading,
            modifier = Modifier.align(Alignment.Center)
        ) {
            LottieAnimation(
                composition = loadingComposition,
                progress = {
                    loadingAnimationState.progress
                },
                modifier = Modifier
                    .size(180.dp)
            )
        }

        AnimatedVisibility(
            visible = weatherData != null && !isLoading,
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier.align(Alignment.TopCenter)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .verticalScroll(rememberScrollState(), enabled = true)
            ) {
                if (lottieRes != null) {
                    LottieAnimation(
                        composition = composition,
                        progress = {
                            logoAnimationState.progress
                        },
                        modifier = Modifier
                            .size(180.dp)
                    )
                }

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
            visible = weatherData == null && !isLoading,
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

        AnimatedVisibility(
            visible = !isLoading,
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            SearchTextField(
                textState = textState,
                focusRequester = focusRequester,
                focusManager = focusManager,
                onValueChange = { textState = it },
                onSearchClick = { weatherViewModel.getWeather(textState.text) },
                onFocus = { if (it.hasFocus) textState = TextFieldValue("") }
            )
        }
    }
}

