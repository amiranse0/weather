package com.example.weather.data.model.remote

import com.google.gson.annotations.SerializedName as SN

data class RemoteHour(
    @SN("chance_of_rain")
    val chanceOfRain: Int,

    @SN("chance_of_snow")
    val chanceOfSnow: Int,

    val cloud: Int,

    val condition: Condition,

    @SN("feelslike_c")
    val feelsLikeTemperature: Double,

    val humidity: Int,

    val time: String,

    val uv: Double,

    @SN("will_it_rain")
    val willItRain: Int,

    @SN("will_it_snow")
    val willItSnow: Int,

    @SN("gust_kph")
    val gustInKph: Double,

    @SN("is_day")
    val isDay: Int,

    @SN("precip_mm")
    val precipitation: Double,

    @SN("temp_c")
    val temperature: Double,

    @SN("vis_km")
    val visibility: Double,

    @SN("wind_dir")
    val windDirection: String,

    @SN("wind_kph")
    val windSpeed: Double
)