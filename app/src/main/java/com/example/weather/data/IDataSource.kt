package com.example.weather.data

import com.example.weather.data.model.current.CurrentWeather

interface IDataSource {
    suspend fun getCurrentWeather(query: Map<String, String>): CurrentWeather
}