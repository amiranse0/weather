package com.example.weather.di

import android.content.Context
import com.example.weather.data.domain.local.AppDatabase
import com.example.weather.data.domain.local.WeatherDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getDatabase(context)
    }

    @Provides
    fun provideWeatherDao(appDatabase: AppDatabase) = appDatabase.weatherDao()

    @Provides
    fun provideCurrentWeatherDao(appDatabase: AppDatabase) = appDatabase.currentWeatherDao()

    @Provides
    fun provideForecastDao(appDatabase: AppDatabase) = appDatabase.forecastDao()

    @Provides
    fun provideHourDao(appDatabase: AppDatabase) = appDatabase.hourDao()

}