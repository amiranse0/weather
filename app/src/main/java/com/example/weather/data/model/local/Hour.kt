package com.example.weather.data.model.local

import androidx.room.ColumnInfo as CI
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.weather.data.model.remote.Condition

@Entity(tableName = "hour")
data class Hour(
    @PrimaryKey
    @CI(name = "hour")
    private val hour: String,
    @CI(name = "chance_of_rain")
    private val chanceOfRain: Int,
    @CI(name = "chance_of_snow")
    private val chanceOfSnow: Int,
    @CI(name = "cloud")
    private val cloud: Int,
    @CI(name = "condition")
    private val condition: Condition,
    @CI(name = "feels_like_temp")
    private val feelsLikeTemperature: Double,
    @CI(name = "humidity")
    private val humidity: Int,
    @CI(name = "time")
    private val time: String,
    @CI(name = "uv")
    private val uv: Double,
    @CI(name = "will_it_rain")
    private val willItRain: Int,
    @CI(name = "will_it_snow")
    private val willItSnow: Int,
    @CI(name = "gust")
    private val gust: Double,
    @CI(name = "precipitation")
    private val precipitation: Double,
    @CI(name = "temp")
    private val temperature: Double,
    @CI(name = "visibility")
    private val visibility: Double,
    @CI(name = "wind_dir")
    private val windDirection: String,
    @CI(name = "wind_speed")
    private val windSpeed: Double
)
