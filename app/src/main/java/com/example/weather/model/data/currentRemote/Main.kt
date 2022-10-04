package com.example.weather.model.data.currentRemote

import com.google.gson.annotations.SerializedName as SN

data class Main(
    @SN("feels_like")
    val feelsLike: Double,
    val humidity: Int,
    val pressure: Int,
    @SN("temp")
    val temperature: Double,
    @SN("temp_max")
    val maximumTemperature: Double,
    @SN("temp_min")
    val minimumTemperature: Double
)