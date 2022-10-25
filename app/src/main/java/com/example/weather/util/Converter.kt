package com.example.weather.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.room.TypeConverter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL


suspend fun urlToBitmap(image:String?):Bitmap?{

    return try {
        val urlString = "http:$image"
        val url = URL(urlString)
        val connection: HttpURLConnection = url
            .openConnection() as HttpURLConnection
        connection.doInput = true
        connection.connect()
        val input: InputStream = withContext(Dispatchers.IO) {
            connection.inputStream
        }
        val bitmap = BitmapFactory.decodeStream(input)
        Bitmap.createScaledBitmap(bitmap, 100, 100, true)
    } catch (e: Exception) {
        Log.d("ERROR", e.toString())
        null
    }
}

class Converter{

    @TypeConverter
    fun toBitmap(byteArray: ByteArray?): Bitmap?{
        if (byteArray == null) return null
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
    }

    @TypeConverter
    fun toByteArray(bitmap: Bitmap?):ByteArray?{
        if (bitmap == null) return null
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        return outputStream.toByteArray()
    }
}

