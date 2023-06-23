package com.fesvieira.whateverweather.helpers

import androidx.compose.ui.graphics.Color
import com.fesvieira.whateverweather.models.WeatherData
import com.fesvieira.whateverweather.ui.theme.CloudGradientBottom
import com.fesvieira.whateverweather.ui.theme.CloudGradientTop
import com.fesvieira.whateverweather.ui.theme.DarkBlue
import com.fesvieira.whateverweather.ui.theme.NightGradientBottom
import com.fesvieira.whateverweather.ui.theme.NightGradientTop
import com.fesvieira.whateverweather.ui.theme.SunnyGradientBottom
import com.fesvieira.whateverweather.ui.theme.SunnyGradientTop

val WeatherData?.weatherGradient: List<Color> get() {
    return when (this?.weather?.condition?.code) {
        1000 ->
            if (this.weather.is_day == 0)  listOf(NightGradientTop, NightGradientBottom)
            else listOf(SunnyGradientTop, SunnyGradientBottom)

        in 1001..1282 -> listOf(CloudGradientTop, CloudGradientBottom)
        else -> listOf(DarkBlue, DarkBlue)
    }
}