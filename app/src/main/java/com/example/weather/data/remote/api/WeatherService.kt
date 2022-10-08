package com.example.weather.data.remote.api

import com.example.weather.data.model.currentRemote.CurrentRemoteResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface WeatherService {
    @GET("v1/forecast.json")
    suspend fun getCurrentWeather(
        @QueryMap query: Map<String, String>
    ): CurrentRemoteResponse

    companion object{
        fun create(): WeatherService {
            val logger = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            return Retrofit.Builder()
                .baseUrl(ApiConfigurations.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(WeatherService::class.java)
        }
    }
}