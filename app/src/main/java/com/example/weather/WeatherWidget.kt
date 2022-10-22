package com.example.weather

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.util.Log
import android.widget.RemoteViews
import com.example.weather.data.domain.WeatherRepository
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

//@AndroidEntryPoint
class WeatherWidget /*@Inject constructor(
    private val repository: WeatherRepository
)*/ : AppWidgetProvider() {
    //private lateinit var sharedPreferences: SharedPreferences
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        /*sharedPreferences = context.getSharedPreferences(
            context.getString(R.string.coordinates),
            Context.MODE_PRIVATE
        )
        val coordinate: String =
            sharedPreferences.getString(context.getString(R.string.coordinates), "Tehran")
                ?: "Toronto"
        val map = mapOf(
            "key" to ApiConfigurations.API_KEY,
            "days" to "7",
            "aqi" to "no",
            "alerts" to "no",
            "q" to coordinate
        )
        runBlocking {
            repository.getData(map).collect{
                when(it){
                    is ResultOf.Success -> {

                    }
                    else -> {}
                }
            }
        }*/

        Log.d("WIDGET", "WIDGET")
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }
}


internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int
) {
    val views = RemoteViews(context.packageName, R.layout.weather_widget)

    appWidgetManager.updateAppWidget(appWidgetId, views)
}