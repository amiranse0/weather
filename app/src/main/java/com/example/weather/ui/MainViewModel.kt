package com.example.weather.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.weather.data.domain.WeatherRepository
import com.example.weather.data.domain.local.WeatherAndCurrentWeather
import com.example.weather.util.ResultOf
import com.example.weather.data.model.remote.RemoteWeather
import com.example.weather.data.domain.remote.api.ApiConfigurations
import com.example.weather.data.model.local.Weather
import com.example.weather.data.model.remote.RemoteCurrent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val weatherRepository: WeatherRepository) :
    ViewModel() {

    val weatherLiveData = weatherRepository.getWeatherAndCurrentWeather(
        query = mapOf(
            "key" to ApiConfigurations.API_KEY,
            "days" to "7",
            "aqi" to "no",
            "alerts" to "no",
            "q" to "Tehran"
        )).asLiveData()
}