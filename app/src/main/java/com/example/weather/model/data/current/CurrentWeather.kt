package com.example.weather.model.data.current

data class CurrentWeather(
    private val latitude: Double,
    private val longitude: Double,
    private val mainWeather: String,
    private val descriptionWeather: String,
    private val iconWeather: String,
    private val mainTemperature: Double,
    private val maximumTemperature: Double,
    private val minimumTemperature: Double,
    private val feelsLikeTemperature: Double,
    private val humidity: Int,
    private val pressure: Int,
    private val visibility: Int,
    private val windSpeed: Double,
    private val windDegree: Int,
    private val cloudiness: Int,
    private val timeOfDataCalculation: Int,
    private val country: String,
    private val sunrise: Int,
    private val sunset: Int,
    private val timeZone: Int,
    private val nameOfCity: String

)