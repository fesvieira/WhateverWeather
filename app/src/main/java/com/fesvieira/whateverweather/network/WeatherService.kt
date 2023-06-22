package com.fesvieira.whateverweather.network

import com.fesvieira.whateverweather.models.WeatherData
import com.fesvieira.whateverweather.models.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface WeatherService {
    @GET("/current.json")
    suspend fun getWeather(@Query("q") query: String): Response<WeatherData>
}
