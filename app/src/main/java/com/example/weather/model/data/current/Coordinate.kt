package com.example.weather.model.data.current

import com.google.gson.annotations.SerializedName as SN

data class Coordinate(
    @SN("lat")
    val latitude: Double,
    @SN("lon")
    val longitude: Double,
)