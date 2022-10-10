package com.example.weather.data.model.remote

data class RemoteForecastDay(
    val date: String,
    val remoteDay: RemoteDay,
    val remoteHour: List<RemoteHour>
)