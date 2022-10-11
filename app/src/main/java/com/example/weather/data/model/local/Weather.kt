package com.example.weather.data.model.local

import androidx.room.ColumnInfo as CI
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather")
data class Weather(
    @CI(name = "country_name")
    val countryName: String,
    @CI(name = "local_name")
    val localTime: String,
    @CI(name = "region")
    val region: String,
    @PrimaryKey
    @CI(name = "weather")
    val weather: String, // local time + last update
)