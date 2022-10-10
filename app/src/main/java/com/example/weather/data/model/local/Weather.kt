package com.example.weather.data.model.local

import androidx.room.ColumnInfo as CI
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather")
data class Weather(
    @PrimaryKey(autoGenerate = true)
    private val id: Long,
    @CI(name = "country_name")
    private val countryName: String,
    @CI(name = "local_name")
    private val localTime: String,
    @CI(name = "region")
    private val region: String,
    @CI(name = "current_weather")
    private val currentWeather: String, // local time + last update
    @CI(name = "forecast")
    private val forecast: String // date + last update
)