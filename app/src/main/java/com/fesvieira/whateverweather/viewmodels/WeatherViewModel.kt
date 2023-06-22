package com.fesvieira.whateverweather.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fesvieira.whateverweather.models.Result
import com.fesvieira.whateverweather.models.WeatherData
import com.fesvieira.whateverweather.network.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository
): ViewModel() {

    private val _currentCityWeather = MutableStateFlow<Result<WeatherData>>(Result.Loading)
    val currentCityWeather get() = _currentCityWeather

    fun getWeather(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _currentCityWeather.value = weatherRepository.getWeather(query)
        }
    }
}