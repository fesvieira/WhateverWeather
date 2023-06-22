package com.fesvieira.whateverweather.network

import com.fesvieira.whateverweather.BuildConfig
import com.fesvieira.whateverweather.models.WeatherData
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header

private const val WEATHER_URL = BuildConfig.WEATHER_URL

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(WEATHER_URL)
    .build()

interface WeatherService {
    @GET("/current.json")
    fun getWeather(): WeatherData
}

object WeatherApi {
    val retrofitService: WeatherService by lazy { retrofit.create(WeatherService::class.java) }
}