package com.example.weather.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.net.URL


fun urlToBitmap(image:String):Bitmap{

    val url = URL(image)
    return BitmapFactory.decodeStream(url.openConnection().getInputStream())
}