package com.example.weather.data.model

data class Forecast(
    val date: String,
    val day: Day,
    val hour: List<Hour>
)