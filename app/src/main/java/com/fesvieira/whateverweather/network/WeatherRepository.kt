package com.fesvieira.whateverweather.network

import com.fesvieira.whateverweather.models.WeatherData
import retrofit2.Response
import retrofit2.http.Query
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val weatherService: WeatherService
) {
    suspend fun getWeather(@Query("q") query: String): Response<WeatherData> = weatherService.getWeather(query)
}