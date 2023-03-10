package com.example.weather.data.model.remote

import com.google.gson.annotations.SerializedName as SN

data class RemoteDay(
    @SN("avghumidity")
    val averageHumidity: Double,

    @SN("avgtemp_c")
    val averageTemperature: Double,

    @SN("avgvis_km")
    val averageVisibility: Double,

    @SN("condition")
    val condition: Condition,

    @SN("daily_chance_of_rain")
    val dailyChanceOfRain: Int,

    @SN("daily_chance_of_snow")
    val dailyChanceOfSnow: Int,

    @SN("maxtemp_c")
    val maximumTemperature: Double,

    @SN("maxwind_kph")
    val maximumWindSpeed: Double,

    @SN("mintemp_c")
    val minimumTemperature: Double,

    @SN("totalprecip_mm")
    val totalPrecipitation: Double,

    @SN("uv")
    val uv: Double
)