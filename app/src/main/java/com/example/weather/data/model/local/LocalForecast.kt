package com.example.weather.data.model.local

import androidx.room.ColumnInfo as CI
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "forecast")
data class LocalForecast(
    @CI(name = "weather")
    val weather: Int = 1,
    @CI(name = "date")
    val date:String,
    @CI(name = "average_humidity")
    val averageHumidity: Double,
    @CI(name = "average_temp")
    val averageTemperature: Double,
    @CI(name = "average_visibility")
    val averageVisibility: Double,
    @CI(name = "condition_icon")
    val conditionIcon:String,
    @CI(name = "condition")
    val condition: String,
    @CI(name = "daily_chance_of_rain")
    val dailyChanceOfRain: Int,
    @CI(name = "daily_chance_of_snow")
    val dailyChanceOfSnow: Int,
    @CI(name = "max_temp")
    val maximumTemperature: Double,
    @CI(name = "max_wind")
    val maximumWindSpeed: Double,
    @CI(name = "min_temp_")
    val minimumTemperature: Double,
    @CI(name = "total_precipitation")
    val totalPrecipitation: Double,
    @CI(name = "uv")
    val uv: Double,
    @PrimaryKey
    @CI(name = "number_day")
    val numberDay:Int
)
