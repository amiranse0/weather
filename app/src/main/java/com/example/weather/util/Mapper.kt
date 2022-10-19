package com.example.weather.util

import com.example.weather.data.model.local.*
import com.example.weather.data.model.remote.RemoteWeather
import java.util.Calendar

object Mapper {
    fun weatherRemoteToLocal(remoteWeather: RemoteWeather): LocalWeather {
        return remoteWeather.location.let {
            LocalWeather(
                countryName = it.country,
                localDate = mapRemoteDateToLocalTemplateDate(it.localTime.split(" ").first()),
                region = it.region,
                name = it.name,
                localTime = it.localTime.split(" ")[1]
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
                lastUpdatedTime = it.remoteCurrent.lastUpdatedTime.split(" ")[1],
                lastUpdatedDate = mapRemoteDateToLocalTemplateDate(it.remoteCurrent.lastUpdatedTime.split(" ")[0]),
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
                timeDate = hour.time,
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
                numberDay = numberDay,
                time = hour.time.split(" ")[1],
                date = mapRemoteDateToLocalTemplateDate(hour.time.split(" ")[0])
            )
        }
    }

    fun mapRemoteDateToLocalTemplateDate(date: String): String {
        return date.let {
            val calendar = Calendar.getInstance()
            val listDate: List<String> = it.split("-")
            calendar.set(
                listDate[0].toInt(),
                listDate[1].toInt(),
                listDate[2].toInt()
            )
            val dayOfWeek: String = calendar.get(Calendar.DAY_OF_WEEK).let { it1 ->
                when (it1) {
                    1 -> "Sun"
                    2 -> "Mon"
                    3 -> "Tue"
                    4 -> "Wed"
                    5 -> "Thu"
                    6 -> "Fri"
                    7 -> "Sat"
                    else -> "None"
                }
            }
            val month: String = calendar.get(Calendar.MONTH).let { it2 ->
                when (it2) {
                    0 -> "Jan"
                    1 -> "Feb"
                    2 -> "Mar"
                    3 -> "Apr"
                    4 -> "May"
                    5 -> "Jun"
                    6 -> "Jul"
                    7 -> "Aug"
                    8 -> "Sep"
                    9 -> "Oct"
                    10 -> "Nov"
                    11 -> "Dec"
                    else -> "None"
                }
            }
            "$dayOfWeek, ${calendar.get(Calendar.DATE)} $month"
        }
    }
}