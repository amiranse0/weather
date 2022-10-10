package com.example.weather.data.domain.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.weather.data.model.local.CurrentWeather
import com.example.weather.data.model.local.Forecast
import com.example.weather.data.model.local.Hour
import com.example.weather.data.model.local.Weather

@Database(entities = [Hour::class, Weather::class, CurrentWeather::class, Forecast::class], version = 1)
abstract class AppDatabase:RoomDatabase() {

    abstract fun weatherDao(): WeatherDao
    abstract fun currentWeatherDao():CurrentWeatherDao
    abstract fun forecastDao():ForecastDao
    abstract fun hourDao(): HourDao

    companion object{
        @Volatile
        private var INSTANCE:AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase{
            val tempInstance = INSTANCE

            if (tempInstance != null){
                return tempInstance
            } else{
                synchronized(this){
                    val instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "app_database",
                    ).build()
                    INSTANCE = instance
                    return instance
                }
            }
        }
    }
}