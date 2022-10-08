package com.example.weather.data.model.currentRemote

import com.google.gson.annotations.SerializedName as SN

data class CurrentRemoteResponse(
    val clouds: Clouds,
    @SN("coord")
    val coordinate: Coordinate,
    @SN("dt")
    val timeOfDataCalculation: Int,
    val main: Main,
    val name: String,
    @SN("sys")
    val system: SystemMetrics,
    @SN("timezone")
    val timeZone: Int,
    val visibility: Int,
    val weather: List<Weather>,
    val wind: Wind,
)