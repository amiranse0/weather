package com.example.weather.data

import com.example.weather.data.model.Weather

interface IDataSource {
    suspend fun getCurrentWeather(query: Map<String, String>): Weather
}