package com.fesvieira.whateverweather.widgets

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.glance.ColorFilter
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.size
import androidx.glance.text.Text
import androidx.glance.unit.ColorProvider
import com.fesvieira.whateverweather.R
import com.fesvieira.whateverweather.ui.theme.GlanceTypography

class WeatherWidget : GlanceAppWidget() {
    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            Content()
        }
    }
}

@Composable
fun Content() {
    Column(
        verticalAlignment = Alignment.Vertical.CenterVertically,
        horizontalAlignment = Alignment.Horizontal.CenterHorizontally,
        modifier = GlanceModifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Text(
            text = "City Name",
            style = GlanceTypography.bodyLarge
        )

        Image(
            provider = ImageProvider(R.drawable.ic_search),
            contentDescription = null,
            colorFilter = ColorFilter.tint(ColorProvider(R.color.white)),
            modifier = GlanceModifier.size(40.dp)
        )

        Text(
            text = "22ºC",
            style = GlanceTypography.bodyLarge
        )
    }
}