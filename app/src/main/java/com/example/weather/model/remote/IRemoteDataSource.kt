package com.example.weather.model.remote

import com.example.weather.model.data.current.CurrentWeather
import kotlinx.coroutines.flow.Flow

interface IRemoteDataSource {

    suspend fun getCurrentWeather(query: Map<String, String>): Flow<CurrentWeather>
}