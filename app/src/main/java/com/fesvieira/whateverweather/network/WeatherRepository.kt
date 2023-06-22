package com.fesvieira.whateverweather.network

import com.fesvieira.whateverweather.models.BaseRepository
import com.fesvieira.whateverweather.models.Result
import com.fesvieira.whateverweather.models.WeatherData
import retrofit2.http.Query
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val weatherService: WeatherService
): BaseRepository() {
    suspend fun getWeather(@Query("q") query: String): Result<WeatherData>  {
        return apiCall { weatherService.getWeather(query) }
    }
}