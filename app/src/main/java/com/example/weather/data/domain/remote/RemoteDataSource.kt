package com.example.weather.data.domain.remote

import com.example.weather.data.domain.IDataSource
import com.example.weather.data.model.remote.RemoteWeather
import com.example.weather.data.domain.remote.api.WeatherService
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val service: WeatherService
): IDataSource {
    override suspend fun getCurrentWeather(query: Map<String, String>): RemoteWeather {
        return service.getCurrentWeather(query = query)
    }
}