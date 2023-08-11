package com.fesvieira.whateverweather.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.fesvieira.whateverweather.helpers.formatTemperature
import com.fesvieira.whateverweather.models.ForecastDay
import com.fesvieira.whateverweather.ui.theme.TextFieldBackground
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun NextDayChip(
    forecastDay: ForecastDay
) {
    val firstApiFormat = remember { DateTimeFormatter.ofPattern("yyyy-MM-dd") }
    val date = remember {
        LocalDate.parse(forecastDay.date , firstApiFormat)
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(TextFieldBackground, RoundedCornerShape(32.dp))
            .padding(vertical = 16.dp, horizontal = 8.dp)
    ) {
        Text(text = "${date.monthValue}/${date.dayOfMonth}".format(forecastDay.date), color = Color.White, fontSize = 20.sp)

        AsyncImage(
            model = "https:${forecastDay.day.condition.icon}",
            contentDescription = null,
            modifier = Modifier
                .size(48.dp)
        )

        Text(text = forecastDay.day.avgtemp_c.formatTemperature, color = Color.White, fontSize = 20.sp)
    }
}