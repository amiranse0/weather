package com.example.weather.model.data.current

import com.google.gson.annotations.SerializedName as SN

data class CurrentResponse(
    val clouds: Clouds,
    @SN("coord")
    val coordinate: Coordinate,
    @SN("dt")
    val timeOfDataCalculation: Int,
    val main: Main,
    val name: String,
    @SN("sys")
    val system: Sys,
    @SN("timezone")
    val timeZone: Int,
    val visibility: Int,
    val weather: List<Weather>,
    val wind: Wind
)