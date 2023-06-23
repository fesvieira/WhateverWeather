package com.fesvieira.whateverweather.helpers

import com.fesvieira.whateverweather.R
import com.fesvieira.whateverweather.models.WeatherData

val WeatherData?.weatherLottie: Int? get() {
    val code = this?.weather?.condition?.code ?: return null
    return when (code) {
        1000 -> if (this.weather.is_day == 0) R.raw.clear_night else R.raw.sunny
        in 1001..1282 -> R.raw.clouds
        else -> null
    }
}