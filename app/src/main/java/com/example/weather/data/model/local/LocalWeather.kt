package com.example.weather.data.model.local

import androidx.room.ColumnInfo as CI
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather")
data class LocalWeather(
    @CI(name = "country_name")
    val countryName: String,
    @CI(name = "local_date")
    val localDate: String,
    @CI(name = "local_time")
    val localTime: String,
    @CI(name = "region")
    val region: String,
    @CI(name = "city")
    val name:String,
    @PrimaryKey
    @CI(name = "weather")
    val weather: Int = 1
)