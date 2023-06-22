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
import com.fesvieira.whateverweather.viewmodels.WeatherViewModel

@Composable
fun MainNavHost() {
    val navController = rememberNavController()
    val weatherViewModel = hiltViewModel<WeatherViewModel>()

    NavHost(navController = navController, startDestination = "a") {
        composable("a") {
            LaunchedEffect(key1 = Unit) {
                weatherViewModel.getWeather("maringa")
            }
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                Text(text = "Ola")
            }
        }
    }
}