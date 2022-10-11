package com.example.weather.data.model.remote

import com.google.gson.annotations.SerializedName as SN

data class Condition(
    @SN("code")
    val code: Int,
    @SN("icon")
    val icon: String,
    @SN("text")
    val text: String
)