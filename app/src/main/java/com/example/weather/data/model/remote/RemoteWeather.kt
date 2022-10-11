package com.example.weather.data.model.remote

import com.google.gson.annotations.SerializedName as SN


data class RemoteWeather(
    @SN("current")
    val remoteCurrent: RemoteCurrent,
    @SN("forecast")
    val remoteForecast: RemoteForecast,
    @SN("location")
    val location: Location
)