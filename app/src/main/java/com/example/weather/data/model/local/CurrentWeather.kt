package com.example.weather.data.model.local

import androidx.room.ColumnInfo as CI
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "current")
data class CurrentWeather(
    @PrimaryKey
    @CI(name = "current_weather")
    private val currentWeather:String, // local time + last update
    @CI(name = "cloud")
    private val cloud: Int,
    @CI(name = "condition_icon")
    private val conditionIcon:String,
    @CI(name = "condition")
    private val condition:String, // condition text
    @CI(name = "feels_like_temp")
    private val feelsLikeTemperature:Double,
    @CI(name = "gust")
    private val gust:Double,
    @CI(name = "humidity")
    private val humidity: Int,
    @CI(name = "last_update")
    private val lastUpdated: String,
    @CI(name = "precipitation")
    private val precipitation: Double,
    @CI(name = "temperature")
    val temperature: Double,
    @CI(name = "visibility")
    private val visibility:Double,
    @CI(name = "uv")
    private val uv:Double,
    @CI(name = "wind_dir")
    private val windDirection: String,
    @CI(name="wind_kph")
    private val windSpeed: Double
)