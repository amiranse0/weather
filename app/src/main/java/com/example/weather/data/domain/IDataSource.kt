package com.example.weather.data.domain

import com.example.weather.data.model.remote.RemoteWeather

interface IDataSource {
    suspend fun getCurrentWeather(query: Map<String, String>): RemoteWeather
}