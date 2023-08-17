package com.fesvieira.whateverweather.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.fesvieira.whateverweather.helpers.formatTemperature
import com.fesvieira.whateverweather.models.ForecastDay
import com.fesvieira.whateverweather.ui.theme.TextFieldBackground
import com.fesvieira.whateverweather.ui.theme.Typography
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun NextDayChip(
    forecastDay: ForecastDay,
    useCelsius: Boolean
) {
    val firstApiFormat = remember { DateTimeFormatter.ofPattern("yyyy-MM-dd") }
    val date = remember {
        LocalDate.parse(forecastDay.date, firstApiFormat)
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(TextFieldBackground, RoundedCornerShape(64.dp))
            .padding(vertical = 16.dp, horizontal = 8.dp)
            .widthIn(min = 48.dp)
    ) {
        Text(
            text = "${date.monthValue}/${date.dayOfMonth}".format(forecastDay.date),
            color = Color.White,
            style = Typography.bodySmall
        )

        AsyncImage(
            model = "https:${forecastDay.day.condition.icon}",
            contentDescription = null,
            modifier = Modifier
                .size(40.dp)
        )

        Text(
            text = if (useCelsius) forecastDay.day.avgtemp_c.formatTemperature else forecastDay.day.avgtemp_f.formatTemperature,
            color = Color.White,
            style = Typography.bodySmall
        )
    }
}