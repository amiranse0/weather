package com.example.weather.data.model.remote

import com.google.gson.annotations.SerializedName as SN

data class RemoteForecastDay(
    @SN("date")
    val date: String,
    @SN("day")
    val remoteDay: RemoteDay,
    @SN("hour")
    val remoteHour: List<RemoteHour>
)