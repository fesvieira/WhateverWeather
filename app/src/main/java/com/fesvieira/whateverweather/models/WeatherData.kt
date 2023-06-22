package com.fesvieira.whateverweather.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WeatherData(
    @Json(name = "location")
    val location: Location
)

@JsonClass(generateAdapter = true)
data class Location(
    @Json(name = "name")
    val name: String,
    @Json(name = "region")
    val region: String,
    @Json(name = "country")
    val country: String,
    @Json(name = "lat")
    val lat: Float,
    @Json(name = "lon")
    val lon: Float,
    @Json(name = "tz_id")
    val tz_id: String,
    @Json(name = "localtime_epoch")
    val localtime_epoch: Long,
    @Json(name = "localtime")
    val localtime: String
)