package com.example.weather.data.model.remote

import com.google.gson.annotations.SerializedName

data class Alerts(
    @SerializedName("alert")
    val alerts: List<Alert>
)
