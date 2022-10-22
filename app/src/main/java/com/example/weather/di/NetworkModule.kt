package com.example.weather.di

import com.example.weather.data.domain.remote.api.WeatherService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideWeatherService(): WeatherService {
        return WeatherService.create()
    }
}