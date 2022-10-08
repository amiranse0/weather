package com.example.weather.di

import com.example.weather.data.IDataSource
import com.example.weather.data.remote.RemoteDataSource
import com.example.weather.data.remote.api.WeatherService
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RemoteWeatherDataSource

@InstallIn(SingletonComponent::class)
@Module
abstract class NetworkModule {

    companion object{
        @Singleton
        @Provides
        fun provideUnsplashService(): WeatherService {
            return WeatherService.create()
        }
    }

    @Binds
    @RemoteWeatherDataSource
    @Singleton
    abstract fun provideRemoteWeatherDataSource(dataSource: RemoteDataSource): IDataSource
}