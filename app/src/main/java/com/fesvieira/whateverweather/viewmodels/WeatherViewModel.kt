package com.fesvieira.whateverweather.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fesvieira.whateverweather.models.Result
import com.fesvieira.whateverweather.models.WeatherData
import com.fesvieira.whateverweather.network.WeatherRepository
import com.fesvieira.whateverweather.network.WeatherService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.http.Query
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository
): ViewModel() {
    fun getWeather(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val a = weatherRepository.getWeather("London")) {
                is Result.Error -> {
                    println(a.exception)
                }

                is Result.Success -> {
                    println(a.data)
                }
                else -> {}
            }
        }
    }
}