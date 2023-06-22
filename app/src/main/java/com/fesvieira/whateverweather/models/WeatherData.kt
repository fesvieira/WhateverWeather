package com.fesvieira.whateverweather.models

import com.squareup.moshi.Json

data class WeatherData(
    val location: Location,
    @Json(name = "current")
    val weather: CurrentWeather
)

data class Location(
    val name: String,
    val region: String,
    val country: String,
    val lat: Float,
    val lon: Float,
    val tz_id: String,
    val localtime_epoch: Long,
    val localtime: String
)

data class CurrentWeather(
    val last_updated_epoch: Long,
    val last_updated: String,
    val temp_c: Double,
    val temp_f: Double,
    val is_day: Int,
    val condition: Condition,
    val wind_mph: Double,
    val wind_kph: Double,
    val wind_degree: Int,
    val wind_dir: String,
    val pressure_mb: Double,
    val pressure_in: Double,
    val precip_mm: Double,
    val precip_in: Double,
    val humidity: Int,
    val cloud: Int,
    val feelslike_c: Double,
    val feelslike_f: Double,
    val vis_km: Int,
    val vis_miles: Int,
    val uv: Int,
    val gust_mph: Double,
    val gust_kph: Double,
)

data class Condition(
    val text: String,
    val icon: String,
    val code: Int,
)



