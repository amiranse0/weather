package com.example.weather.data.domain

import com.example.weather.data.model.local.*
import com.example.weather.data.model.remote.Location
import com.example.weather.data.model.remote.RemoteWeather

object Mapper {
    fun weatherRemoteToLocal(remoteWeather: RemoteWeather): LocalWeather {
        return remoteWeather.location.let {
            LocalWeather(
                countryName = it.country,
                localTime = it.localTime,
                region = it.region,
                city = it.name
            )
        }
    }

    fun currentWeatherRemoteToLocal(remoteWeather: RemoteWeather): LocalCurrentWeather {
        return remoteWeather.let {
            LocalCurrentWeather(
                cloud = it.remoteCurrent.cloud,
                conditionIcon = it.remoteCurrent.condition.icon,
                condition = it.remoteCurrent.condition.text,
                feelsLikeTemperature = it.remoteCurrent.feelsLikeTemperature,
                gust = it.remoteCurrent.gustInKph,
                humidity = it.remoteCurrent.humidity,
                lastUpdated = it.remoteCurrent.lastUpdatedTime,
                precipitation = it.remoteCurrent.precipitation,
                temperature = it.remoteCurrent.temperature,
                visibility = it.remoteCurrent.visibility,
                uv = it.remoteCurrent.uv,
                windSpeed = it.remoteCurrent.windSpeed,
                windDirection = it.remoteCurrent.windDirection
            )
        }
    }

    fun forecastRemoteToLocal(remoteWeather: RemoteWeather, numberDay: Int): LocalForecast {
        return remoteWeather.let {
            val day = it.remoteForecast.remoteForecastDays[numberDay].remoteDay
            LocalForecast(
                date = it.remoteForecast.remoteForecastDays[numberDay].date,
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

    fun hourRemoteToLocal(
        remoteWeather: RemoteWeather,
        numberHour: Int,
        numberDay: Int
    ): LocalHour {
        return remoteWeather.let {
            val hour = it.remoteForecast.remoteForecastDays[numberDay].remoteHour[numberHour]
            LocalHour(
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
    /*fun localToRemote(
        localWeatherAndCurrentWeather: WeatherAndCurrentWeather,
        localWeatherWithForecasts: List<WeatherWithForecasts>,
        localForecastsWithHours: List<ForecastWithHours>
    ): RemoteWeather {
        val location:Location = localWeatherAndCurrentWeather.localWeather.let {
            Location(
                country = it.countryName,
                localTime = it.localTime,
                name = it.city,
                region = it.region
            )
        }
    }*/

}