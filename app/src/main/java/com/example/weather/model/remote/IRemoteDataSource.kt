package com.example.weather.model.remote

import com.example.weather.model.data.current.CurrentResponse
import kotlinx.coroutines.flow.Flow

interface IRemoteDataSource {

    suspend fun getCurrentWeather(query: Map<String, String>): Flow<CurrentResponse>
}