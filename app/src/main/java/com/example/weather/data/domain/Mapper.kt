package com.example.weather.data.domain

import com.example.weather.data.model.local.*
import com.example.weather.data.model.remote.RemoteWeather

object Mapper {
    fun weatherRemoteToLocal(remoteWeather: RemoteWeather): LocalWeather {
        remoteWeather.apply {
            return LocalWeather(
                countryName = location.country,
                localTime = location.localTime,
                region = location.region
            )
        }
    }

    fun currentWeatherRemoteToLocal(remoteWeather: RemoteWeather): LocalCurrentWeather {
        remoteWeather.apply {
            return LocalCurrentWeather(
                cloud = remoteCurrent.cloud,
                conditionIcon = remoteCurrent.condition.icon,
                condition = remoteCurrent.condition.text,
                feelsLikeTemperature = remoteCurrent.feelsLikeTemperature,
                gust = remoteCurrent.gustInKph,
                humidity = remoteCurrent.humidity,
                lastUpdated = remoteCurrent.lastUpdatedTime,
                precipitation = remoteCurrent.precipitation,
                temperature = remoteCurrent.temperature,
                visibility = remoteCurrent.visibility,
                uv = remoteCurrent.uv,
                windSpeed = remoteCurrent.windSpeed,
                windDirection = remoteCurrent.windDirection
            )
        }
    }

    fun forecastRemoteToLocal(remoteWeather: RemoteWeather, numberDay: Int): LocalForecast {
        remoteWeather.apply {
            val day = remoteForecast.remoteForecastDays[numberDay].remoteDay
            return LocalForecast(
                date = remoteForecast.remoteForecastDays[numberDay].date,
                averageHumidity = day.averageHumidity,
                averageTemperature = day.averageTemperature,
                averageVisibility = day.averageVisibility,
                conditionIcon = day.condition.icon,
                condition = day.condition.text,
                dailyChanceOfRain = day.dailyChanceOfRain,
                dailyChanceOfSnow = day.dailyChanceOfSnow,
                maximumTemperature = day.maximumTemperature,
                minimumTemperature = day.minimumTemperature,
                maximumWindSpeed = day.maximumWindSpeed,
                totalPrecipitation = day.totalPrecipitation,
                uv = day.uv,
                numberDay = numberDay
            )
        }
    }

    fun hourRemoteToLocal(remoteWeather: RemoteWeather, numberHour: Int, numberDay: Int): LocalHour {
        remoteWeather.apply {
            val hour = remoteForecast.remoteForecastDays[numberDay].remoteHour[numberHour]
            return LocalHour(
                chanceOfRain = hour.chanceOfRain,
                chanceOfSnow = hour.chanceOfSnow,
                cloud = hour.cloud,
                condition = hour.condition.text,
                conditionIcon = hour.condition.icon,
                feelsLikeTemperature = hour.feelsLikeTemperature,
                humidity = hour.humidity,
                time = hour.time,
                uv = hour.uv,
                willItRain = hour.willItRain,
                willItSnow = hour.willItSnow,
                gust = hour.gustInKph,
                precipitation = hour.precipitation,
                temperature = hour.temperature,
                visibility = hour.visibility,
                windDirection = hour.windDirection,
                windSpeed = hour.windSpeed,
                numberHour = numberHour,
                numberDay = numberDay
            )
        }
    }

    //TODO("create a mapper that map local to remote")

}