package com.example.weather.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather.data.domain.WeatherRepository
import com.example.weather.data.domain.remote.api.ApiConfigurations
import com.example.weather.data.model.local.ForecastWithHours
import com.example.weather.data.model.local.WeatherAndCurrentWeather
import com.example.weather.data.model.local.WeatherWithForecasts
import com.example.weather.util.ResultOf
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val weatherRepository: WeatherRepository) :
    ViewModel() {

    private val queryMap = mutableMapOf(
        "key" to ApiConfigurations.API_KEY,
        "days" to "7",
        "aqi" to "no",
        "alerts" to "no"
    )

    private val _weatherAndCurrentWeatherStateFlow: MutableStateFlow<ResultOf<WeatherAndCurrentWeather>> =
        MutableStateFlow(ResultOf.Loading)
    val weatherAndCurrentWeatherStateFlow = _weatherAndCurrentWeatherStateFlow
    private val _weatherWithForecastsStateFlow: MutableStateFlow<ResultOf<WeatherWithForecasts>> =
        MutableStateFlow(ResultOf.Loading)
    val weatherWithForecastsStateFlow = _weatherWithForecastsStateFlow
    private val _forecastWithHoursStateFlow: MutableStateFlow<ResultOf<ForecastWithHours>> =
        MutableStateFlow(ResultOf.Loading)
    val forecastWithHoursStateFlow = _forecastWithHoursStateFlow

    fun getWeathers(query:String){
        viewModelScope.launch {
            val tempQuery = queryMap
            tempQuery["q"] = query
            val weathers = weatherRepository.getWeathers(tempQuery)

            _weatherAndCurrentWeatherStateFlow.update {
                weathers.first.value
            }
        }
    }

}