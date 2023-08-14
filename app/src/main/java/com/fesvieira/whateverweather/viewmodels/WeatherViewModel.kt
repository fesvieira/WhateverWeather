package com.fesvieira.whateverweather.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fesvieira.whateverweather.models.Result
import com.fesvieira.whateverweather.models.WeatherData
import com.fesvieira.whateverweather.repository.UserPreferencesRepository
import com.fesvieira.whateverweather.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val userPreferencesRepository: UserPreferencesRepository
): ViewModel() {

    private val _currentCityWeather = MutableStateFlow<Result<WeatherData>>(Result.Loading)
    val currentCityWeather get() = _currentCityWeather

    val useCelsius: StateFlow<Boolean> =
        userPreferencesRepository
            .useCelsius
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5_000),
                false
            )

    init {
        viewModelScope.launch {
            getWeather(userPreferencesRepository.currentCity.first())
        }
    }

    fun getWeather(query: String) {
        _currentCityWeather.value = Result.Loading
        viewModelScope.launch(Dispatchers.IO) {
            val result = weatherRepository.getWeather(query)
            when (result) {
                is Result.Error -> println(result.exception)
                Result.Loading -> {}
                is Result.Success -> {
                    userPreferencesRepository.saveCurrentCity(result.data.location.name)
                }
            }
            _currentCityWeather.value = result
        }
    }

    fun setTempUnit(useCelsius: Boolean) = viewModelScope.launch {
        userPreferencesRepository.saveUseCelsius(useCelsius)
    }
}