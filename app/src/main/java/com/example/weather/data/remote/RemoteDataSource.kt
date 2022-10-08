package com.example.weather.data.remote

import com.example.weather.data.IDataSource
import com.example.weather.data.MapperCurrentWeather
import com.example.weather.data.model.current.CurrentWeather
import com.example.weather.data.remote.api.WeatherService
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val service: WeatherService
): IDataSource {
    override suspend fun getCurrentWeather(query: Map<String, String>): CurrentWeather {
        val result = service.getCurrentWeather(query)
        return MapperCurrentWeather.responseToMain(result)
    }
}