package com.fesvieira.whateverweather.helpers

import com.fesvieira.whateverweather.models.CurrentWeather

fun CurrentWeather.getTemp(useCelsius: Boolean): String {
    return if (useCelsius) this.temp_c.formatTemperature else this.temp_f.formatTemperature
}