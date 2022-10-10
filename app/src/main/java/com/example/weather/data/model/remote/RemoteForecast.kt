package com.example.weather.data.model.remote

import com.google.gson.annotations.SerializedName as SN

data class RemoteForecast(
    @SN("forecastday")
    val remoteForecastDays: List<RemoteForecastDay>
)