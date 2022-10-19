package com.example.weather.data.model.local

import androidx.room.ColumnInfo as CI
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "hour")
data class LocalHour(
    @CI(name = "number_day")
    val numberDay: Int,
    @CI(name = "chance_of_rain")
    val chanceOfRain: Int,
    @CI(name = "chance_of_snow")
    val chanceOfSnow: Int,
    @CI(name = "cloud")
    val cloud: Int,
    @CI(name = "condition")
    val condition: String,
    @CI(name = "condition_icon")
    val conditionIcon: String,
    @CI(name = "feels_like_temp")
    val feelsLikeTemperature: Double,
    @CI(name = "humidity")
    val humidity: Int,
    @CI(name = "time_date")
    @PrimaryKey
    val timeDate: String,
    @CI(name = "time")
    val time: String,
    @CI(name = "date")
    val date: String,
    @CI(name = "uv")
    val uv: Double,
    @CI(name = "will_it_rain")
    val willItRain: Int,
    @CI(name = "will_it_snow")
    val willItSnow: Int,
    @CI(name = "gust")
    val gust: Double,
    @CI(name = "precipitation")
    val precipitation: Double,
    @CI(name = "temp")
    val temperature: Double,
    @CI(name = "visibility")
    val visibility: Double,
    @CI(name = "wind_dir")
    val windDirection: String,
    @CI(name = "wind_speed")
    val windSpeed: Double,
    @CI(name = "number_hour")
    val numberHour: Int
)
