package com.example.weather.data.domain.local

import androidx.room.Embedded
import androidx.room.Relation
import com.example.weather.data.model.local.CurrentWeather
import com.example.weather.data.model.local.Forecast
import com.example.weather.data.model.local.Hour
import com.example.weather.data.model.local.Weather

data class WeatherAndCurrentWeather(
    @Embedded
    val weather: Weather,
    @Relation(
        parentColumn = "weather",
        entityColumn = "weather"
    )
    val currentWeather: CurrentWeather
)

data class WeatherWithForecasts(
    @Embedded
    val weather: Weather,
    @Relation(
        parentColumn = "weather",
        entityColumn = "weather"
    )
    val forecasts: List<Forecast>
)

data class ForecastWithHours(
    @Embedded
    val forecast: Forecast,
    @Relation(
        parentColumn = "forecast",
        entityColumn = "forecast"
    )
    val hours: List<Hour>
)