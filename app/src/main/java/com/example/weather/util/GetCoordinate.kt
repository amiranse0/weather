package com.example.weather.util

import android.content.Context
import android.content.SharedPreferences
import com.example.weather.R

fun getCoordinate(context: Context): String {
    val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(context.getString(R.string.coordinates), Context.MODE_PRIVATE)

    return sharedPreferences.getString(context.getString(R.string.coordinates), "Tehran")
        ?: "Toronto"
}