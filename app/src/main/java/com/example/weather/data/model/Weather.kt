package com.example.weather.data.model

data class Weather(
    val current: Current,
    val forecast: List<Forecast>,
    val location: Location
)