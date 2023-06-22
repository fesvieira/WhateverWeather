package com.fesvieira.whateverweather.network

import com.fesvieira.whateverweather.BuildConfig
import com.fesvieira.whateverweather.models.WeatherData
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface WeatherService {
    @GET("/current.json")
    fun getWeather(@Query("q") query: String): Response<WeatherData>
}
