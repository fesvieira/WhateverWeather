package com.fesvieira.whateverweather.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.fesvieira.whateverweather.navigation.Routes.START_SCREEN
import com.fesvieira.whateverweather.screens.StartScreen
import com.fesvieira.whateverweather.viewmodels.WeatherViewModel

@Composable
fun MainNavHost() {
    val navController = rememberNavController()
    val weatherViewModel = hiltViewModel<WeatherViewModel>()

    NavHost(navController = navController, startDestination = START_SCREEN) {
        composable(START_SCREEN) {
            StartScreen(navController, weatherViewModel)
        }
    }
}