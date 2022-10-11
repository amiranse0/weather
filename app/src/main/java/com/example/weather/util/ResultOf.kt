package com.example.weather.util

sealed class ResultOf<out R> {

    data class Success<out T>(val data: T) : ResultOf<T>()
    data class Error(val exception: Exception) : ResultOf<Nothing>()
    object Loading : ResultOf<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
            Loading -> "Loading"
        }
    }
}

