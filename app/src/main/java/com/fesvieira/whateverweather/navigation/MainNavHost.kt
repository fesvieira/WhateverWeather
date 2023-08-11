package com.fesvieira.whateverweather.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.fesvieira.whateverweather.navigation.Routes.START_SCREEN
import com.fesvieira.whateverweather.screens.StartScreen
import com.fesvieira.whateverweather.ui.theme.WhateverWeatherTheme
import com.fesvieira.whateverweather.viewmodels.WeatherViewModel

@Composable
fun MainNavHost() {
    val navController = rememberNavController()
    val weatherViewModel = hiltViewModel<WeatherViewModel>()

    NavHost(navController = navController, startDestination = START_SCREEN) {
        composable(START_SCREEN) {
            StartScreen(weatherViewModel)
        }
    }
}