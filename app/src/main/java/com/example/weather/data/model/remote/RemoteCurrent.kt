package com.example.weather.data.model.remote

import com.google.gson.annotations.SerializedName as SN

data class RemoteCurrent(
    val cloud: Int,
    val condition: Condition,
    @SN("feelslike_c")
    val feelsLikeTemperature: Double,
    @SN("gust_kph")
    val gustInKph: Double,
    val humidity: Int,
    @SN("is_day")
    val isDay: Int,
    @SN("last_updated")
    val lastUpdated: String,
    @SN("precip_mm")
    val precipitation: Double,
    @SN("temp_c")
    val temperature: Double,
    val uv: Double,
    @SN("vis_km")
    val visibility: Double,
    @SN("wind_dir")
    val windDirection: String,
    @SN("wind_kph")
    val windSpeed: Double
)