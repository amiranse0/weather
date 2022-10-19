package com.example.weather.data.model.local

import androidx.room.ColumnInfo as CI
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "current")
data class LocalCurrentWeather(
    @PrimaryKey
    @CI(name = "weather")
    val weather: Int = 1,
    @CI(name = "cloud")
    val cloud: Int,
    @CI(name = "condition_icon")
    val conditionIcon:String,
    @CI(name = "condition")
    val condition:String, // condition text
    @CI(name = "feels_like_temp")
    val feelsLikeTemperature:Double,
    @CI(name = "gust")
    val gust:Double,
    @CI(name = "humidity")
    val humidity: Int,
    @CI(name = "last_update_time")
    val lastUpdatedTime: String,
    @CI(name = "last_updated_date")
    val lastUpdatedDate: String,
    @CI(name = "precipitation")
    val precipitation: Double,
    @CI(name = "temperature")
    val temperature: Double,
    @CI(name = "visibility")
    val visibility:Double,
    @CI(name = "uv")
    val uv:Double,
    @CI(name = "wind_dir")
    val windDirection: String,
    @CI(name="wind_kph")
    val windSpeed: Double
)