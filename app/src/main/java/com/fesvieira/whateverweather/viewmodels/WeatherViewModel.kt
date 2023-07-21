package com.fesvieira.whateverweather.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fesvieira.whateverweather.models.Result
import com.fesvieira.whateverweather.models.WeatherData
import com.fesvieira.whateverweather.repository.WeatherRepository
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
        _currentCityWeather.value = Result.Loading
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = weatherRepository.getWeather(query)) {
                is Result.Error -> println(result.exception)
                Result.Loading -> {}
                is Result.Success -> println(result.data)
            }
            _currentCityWeather.value = weatherRepository.getWeather(query)
        }
    }
}