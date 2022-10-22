package com.example.weather.data.domain

import com.example.weather.data.domain.remote.api.WeatherService
import com.example.weather.data.model.remote.RemoteWeather
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val service: WeatherService
) {
    suspend fun fetch(query: Map<String, String>): RemoteWeather =
        service.getWeather(query)
}