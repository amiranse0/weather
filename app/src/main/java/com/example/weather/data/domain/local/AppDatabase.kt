package com.example.weather.data.domain.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.weather.data.model.local.LocalCurrentWeather
import com.example.weather.data.model.local.LocalForecast
import com.example.weather.data.model.local.LocalHour
import com.example.weather.data.model.local.LocalWeather

@Database(entities = [LocalHour::class, LocalWeather::class, LocalCurrentWeather::class, LocalForecast::class], version = 1)
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