package com.example.weather.data.model

import com.google.gson.annotations.SerializedName as SN

data class Forecast(
    @SN("forecastday")
    val forecastDays: List<ForecastDay>
)