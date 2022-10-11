package com.example.weather.data.model.remote

import com.google.gson.annotations.SerializedName as SN

data class RemoteCurrent(
    @SN("cloud")
    val cloud: Int,
    @SN("condition")
    val condition: Condition,
    @SN("feelslike_c")
    val feelsLikeTemperature: Double,
    @SN("gust_kph")
    val gustInKph: Double,
    @SN("humidity")
    val humidity: Int,
    @SN("is_day")
    val isDay: Int,
    @SN("last_updated")
    val lastUpdatedTime: String,
    @SN("precip_mm")
    val precipitation: Double,
    @SN("temp_c")
    val temperature: Double,
    @SN("uv")
    val uv: Double,
    @SN("vis_km")
    val visibility: Double,
    @SN("wind_dir")
    val windDirection: String,
    @SN("wind_kph")
    val windSpeed: Double
)