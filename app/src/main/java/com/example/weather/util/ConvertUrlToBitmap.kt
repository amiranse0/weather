package com.example.weather.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult

suspend fun urlToBitmap(url:String, context: Context):Bitmap{
    val loader = ImageLoader(context)
    val request = ImageRequest.Builder(context)
        .data(url)
        .build()

    val result:Drawable = (loader.execute(request) as SuccessResult).drawable
    return (result as BitmapDrawable).bitmap
}