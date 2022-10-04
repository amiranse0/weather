package com.example.weather.model

import com.example.weather.model.data.current.CurrentWeather
import com.example.weather.model.data.currentRemote.CurrentRemoteResponse

object MapperCurrentWeather {
    fun responseToMain(response: CurrentRemoteResponse): CurrentWeather {
        return response.let {
            CurrentWeather(
                latitude = it.coordinate.latitude,
                longitude = it.coordinate.longitude,
                mainWeather = it.weather.first().main,
                descriptionWeather = it.weather.first().description,
                iconWeather = it.weather.first().icon,
                mainTemperature = it.main.temperature,
                maximumTemperature = it.main.maximumTemperature,
                minimumTemperature = it.main.minimumTemperature,
                feelsLikeTemperature = it.main.feelsLike,
                humidity = it.main.humidity,
                pressure = it.main.pressure,
                visibility = it.visibility,
                windSpeed = it.wind.speed,
                windDegree = it.wind.degree,
                cloudiness = it.clouds.all,
                timeOfDataCalculation = it.timeOfDataCalculation,
                country = it.system.country,
                sunrise = it.system.sunrise,
                sunset = it.system.sunset,
                timeZone = it.timeZone,
                nameOfCity = it.name
            )
        }
    }
}