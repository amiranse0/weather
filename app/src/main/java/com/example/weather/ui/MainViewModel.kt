package com.example.weather.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weather.data.domain.WeatherRepository
import com.example.weather.data.domain.remote.api.ApiConfigurations
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val weatherRepository: WeatherRepository) :
    ViewModel() {

    private val queryMap = mutableMapOf(
        "ket" to ApiConfigurations.API_KEY,
        "days" to "7",
        "aqi" to "no",
        "alerts" to "no"
    )

    fun autoCurrentWeather(){
        val _queryMap = queryMap
        _queryMap["q"] = "${locationLiveData.value?.first},${locationLiveData.value?.second}"
    }

    var locationLiveData: MutableLiveData<Pair<Double, Double>> = MutableLiveData()


    private val weathers = weatherRepository.getWeathers(query = mapOf(
        "key" to ApiConfigurations.API_KEY,
        "days" to "7",
        "aqi" to "no",
        "alerts" to "no",
        "q" to "${locationLiveData.value?.first},${locationLiveData.value?.second}"
    ))

    val weatherAndCurrentWeatherStateFlow = weathers.first
    val weatherWithForecastsStateFlow = weathers.second
    val forecastWithHoursStateFlow = weathers.third
}