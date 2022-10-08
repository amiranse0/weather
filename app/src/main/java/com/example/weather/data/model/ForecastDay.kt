package com.example.weather.data.model

data class ForecastDay(
    val date: String,
    val day: Day,
    val hour: List<Hour>
)