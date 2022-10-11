package com.example.weather.data.model.remote

import com.google.gson.annotations.SerializedName as SN

data class Location(
    @SN("country")
    val country: String,
    @SN("localtime")
    val localTime: String, /* "2022-10-08 12:36"*/
    @SN("name")
    val name: String,
    @SN("region")
    val region: String,
)