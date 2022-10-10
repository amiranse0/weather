package com.example.weather.data.local

import androidx.room.Database
import com.example.weather.data.model.local.CurrentWeather
import com.example.weather.data.model.local.Forecast
import com.example.weather.data.model.local.Hour
import com.example.weather.data.model.local.Weather

@Database(entities = [Hour::class, Weather::class, CurrentWeather::class, Forecast::class], version = 1)
abstract class AppDatabase {

}