package com.example.weather.data.model.local

import androidx.room.ColumnInfo as CI
import androidx.room.Entity

@Entity(tableName = "weather")
data class Weather(
    @CI(name = "country_name")
    private val countryName: String,
    @CI(name = "local_name")
    private val localTime: String,
    @CI(name = "region")
    private val region: String,
    @CI(name = "weather")
    private val weather: String, // local time + last update
)