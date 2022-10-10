package com.example.weather.data.model.remote

data class RemoteWeather(
    val remoteCurrent: RemoteCurrent,
    val remoteForecast: RemoteForecast,
    val location: Location
)