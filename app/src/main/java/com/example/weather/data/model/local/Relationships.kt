package com.example.weather.data.model.local

import androidx.room.Embedded
import androidx.room.Relation

data class WeatherAndCurrentWeather(
    @Embedded
    val localWeather: LocalWeather,
    @Relation(
        parentColumn = "weather",
        entityColumn = "weather"
    )
    val localCurrentWeather: LocalCurrentWeather
)

data class WeatherWithForecasts(
    @Embedded
    val localWeather: LocalWeather,
    @Relation(
        parentColumn = "weather",
        entityColumn = "weather"
    )
    val localForecasts: List<LocalForecast>
)

data class ForecastWithHours(
    @Embedded
    val localForecast: LocalForecast,
    @Relation(
        parentColumn = "number_day",
        entityColumn = "number_day"
    )
    val localHours: List<LocalHour>
)