package com.example.weather.util

import android.content.Context
import android.net.ConnectivityManager

fun haveNetwork(context: Context):Boolean{
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    return !connectivityManager.isActiveNetworkMetered

}