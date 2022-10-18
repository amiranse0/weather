package com.example.weather.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather.MainApp
import com.example.weather.data.domain.WeatherRepository
import com.example.weather.data.domain.remote.api.ApiConfigurations
import com.example.weather.data.model.local.ForecastWithHours
import com.example.weather.data.model.local.WeatherAndCurrentWeather
import com.example.weather.data.model.local.WeatherWithForecasts
import com.example.weather.util.ResultOf
import com.example.weather.util.haveNetwork
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val weatherRepository: WeatherRepository) :
    ViewModel() {

    private val queryMap = mutableMapOf(
        "key" to ApiConfigurations.API_KEY,
        "days" to "7",
        "aqi" to "no",
        "alerts" to "no"
    )

    suspend fun mainFunction(){
        viewModelScope.launch {
            MainApp.isConnected().collect{
                if (it){
                    TODO("fetch date from remote")
                    TODO("if fetch successful save the data in local")
                    TODO("if fetch failed check data is exist on local")
                    TODO("if exist show it and make a toast that something is wrong.")
                    TODO("if not show error.")
                }else{
                    TODO("Toast that no internet connection")
                    TODO("check exist data in local")
                    TODO("if data exist show it")
                    TODO("if data not exist show error message only")
                }
            }
        }
    }

    private val _weatherAndCurrentWeatherStateFlow: MutableStateFlow<ResultOf<WeatherAndCurrentWeather>> =
        MutableStateFlow(ResultOf.Loading)
    val weatherAndCurrentWeatherStateFlow = _weatherAndCurrentWeatherStateFlow
    private val _weatherWithForecastsStateFlow: MutableStateFlow<ResultOf<List<WeatherWithForecasts>>> =
        MutableStateFlow(ResultOf.Loading)
    val weatherWithForecastsStateFlow = _weatherWithForecastsStateFlow
    private val _forecastWithHoursStateFlow: MutableStateFlow<ResultOf<List<ForecastWithHours>>> =
        MutableStateFlow(ResultOf.Loading)
    val forecastWithHoursStateFlow = _forecastWithHoursStateFlow

    fun getWeathers(query:String){
        viewModelScope.launch {
            val tempQuery = queryMap
            tempQuery["q"] = query
            val weathers = weatherRepository.getWeathers(tempQuery)

            _weatherAndCurrentWeatherStateFlow.update {
                weathers.first.value
            }
            _weatherWithForecastsStateFlow.update {
                weathers.second.value
            }
            _forecastWithHoursStateFlow.update {
                weathers.third.value
            }
        }
    }

}