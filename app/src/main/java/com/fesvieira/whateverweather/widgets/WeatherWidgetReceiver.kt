package com.fesvieira.whateverweather.widgets

import android.appwidget.AppWidgetManager
import android.content.Context
import android.widget.RemoteViews
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import com.fesvieira.whateverweather.R
import com.fesvieira.whateverweather.models.Result
import com.fesvieira.whateverweather.repository.WeatherRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class WeatherWidgetReceiver @Inject constructor(
    private val weatherRepository: WeatherRepository
) : GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget = WeatherWidget()

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)

        CoroutineScope(Dispatchers.IO).launch {
            when (weatherRepository.getWeather("Maringa")) {
                is Result.Success -> {
                    //TODO: Update widget
                }
                else -> {}
            }
        }
    }
}