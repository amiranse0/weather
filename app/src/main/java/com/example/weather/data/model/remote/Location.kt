package com.example.weather.data.model.remote

import com.google.gson.annotations.SerializedName as SN

data class Location(
    val country: String,
    @SN("localtime")
    val localTime: String, /* "2022-10-08 12:36"*/
    val name: String,
    val region: String,
)