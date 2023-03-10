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

    suspend fun currentWeatherRemoteToLocal(remoteWeather: RemoteWeather): LocalCurrentWeather {
        return remoteWeather.let {
            LocalCurrentWeather(
                cloud = it.remoteCurrent.cloud,
                conditionIcon = urlToBitmap(it.remoteCurrent.condition.icon),
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

    suspend fun forecastRemoteToLocal(remoteWeather: RemoteWeather, numberDay: Int): LocalForecast {
        return remoteWeather.let {
            val day = it.remoteForecast.remoteForecastDays[numberDay].remoteDay
            LocalForecast(
                date = mapRemoteDateToLocalTemplateDate(it.remoteForecast.remoteForecastDays[numberDay].date),
                averageHumidity = day.averageHumidity,
                averageTemperature = day.averageTemperature,
                averageVisibility = day.averageVisibility,
                conditionIcon = urlToBitmap(day.condition.icon),
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

    private fun mapRemoteDateToLocalTemplateDate(date: String): String {
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
                    4 -> "Sun"
                    5 -> "Mon"
                    6 -> "Tue"
                    7 -> "Wed"
                    1 -> "Thu"
                    2 -> "Fri"
                    3 -> "Sat"
                    else -> "None"
                }
            }
            val month: String = calendar.get(Calendar.MONTH).let { it2 ->
                when (it2) {
                    1 -> "Jan"
                    2 -> "Feb"
                    3 -> "Mar"
                    4 -> "Apr"
                    5 -> "May"
                    6 -> "Jun"
                    7 -> "Jul"
                    8 -> "Aug"
                    9 -> "Sep"
                    10 -> "Oct"
                    11 -> "Nov"
                    12 -> "Dec"
                    else -> "None"
                }
            }
            "$dayOfWeek, ${calendar.get(Calendar.DATE)} $month"
        }
    }
}