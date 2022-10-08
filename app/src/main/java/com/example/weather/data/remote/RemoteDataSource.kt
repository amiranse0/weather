package com.example.weather.data.remote

import com.example.weather.data.IDataSource
import com.example.weather.data.model.Weather
import com.example.weather.data.remote.api.WeatherService
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val service: WeatherService
): IDataSource {
    override suspend fun getCurrentWeather(query: Map<String, String>): Weather {
        return service.getCurrentWeather(query)
    }
}