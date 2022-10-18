package com.example.weather.data.model.remote

import com.google.gson.annotations.SerializedName as SN

data class Alert(
    @SN("areas")
    val areas: String,
    @SN("category")
    val category: String,
    @SN("certainty")
    val certainty: String,
    @SN("desc")
    val description: String,
    @SN("effective")
    val effective: String,
    @SN("event")
    val event: String,
    @SN("expires")
    val expires: String,
    @SN("headlines")
    val headline: String,
    @SN("instruction")
    val instruction: String,
    @SN("note")
    val note: String,
    @SN("severity")
    val severity: String,
    @SN("urgency")
    val urgency: String
)