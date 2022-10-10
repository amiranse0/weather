package com.example.weather.data.model.local

import androidx.room.ColumnInfo as CI
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "forecast")
data class Forecast(
    @PrimaryKey
    @CI(name = "forecast")
    private val forecast:String,
    @CI(name = "date")
    private val date:String,
    @CI(name = "average_humidity")
    private val averageHumidity: Double,
    @CI(name = "average_temp")
    private val averageTemperature: Double,
    @CI(name = "average_visibility")
    private val averageVisibility: Double,
    @CI(name = "condition_icon")
    private val conditionIcon:String,
    @CI(name = "condition")
    private val condition: String,
    @CI(name = "daily_chance_of_rain")
    private val dailyChanceOfRain: Int,
    @CI(name = "daily_chance_of_snow")
    private val dailyChanceOfSnow: Int,
    @CI(name = "max_temp")
    private val maximumTemperature: Double,
    @CI(name = "max_wind")
    private val maximumWindSpeed: Double,
    @CI(name = "min_temp_")
    private val minimumTemperature: Double,
    @CI(name = "total_precipitation")
    private val totalPrecipitation: Double,
    @CI(name = "uv")
    private val uv: Double,
    @CI(name = "hour")
    private val hour: String //time + last update
)
