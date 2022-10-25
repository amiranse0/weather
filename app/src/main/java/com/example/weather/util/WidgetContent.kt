package com.example.weather.util

import android.graphics.Bitmap

data class WidgetContent(
    val territoryName: String,
    val icon: Bitmap?,
    val date: String,
    val temperature: Double
)

