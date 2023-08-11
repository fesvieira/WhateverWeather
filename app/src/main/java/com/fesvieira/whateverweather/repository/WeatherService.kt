package com.fesvieira.whateverweather.repository

import com.fesvieira.whateverweather.models.WeatherData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    @GET("/forecast.json")
    suspend fun getWeather(
        @Query("q") query: String,
        @Query("days") days: Int = 3,
    ): Response<WeatherData>
}
