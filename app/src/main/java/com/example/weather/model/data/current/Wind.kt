package com.example.weather.model.data.current

import com.google.gson.annotations.SerializedName as SN

data class Wind(
    @SN("deg")
    val degree: Int,
    val speed: Double
)