package com.example.weather.model.remote

import com.example.weather.model.data.current.CurrentWeather
import com.example.weather.model.remote.api.WeatherService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val service: WeatherService
): IRemoteDataSource {
    override suspend fun getCurrentWeather(query: Map<String, String>): Flow<CurrentWeather> {
        val response = service.getCurrentWeather(query)
    }
}