package com.example.weather

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.RemoteViews
import com.example.weather.data.domain.WeatherRepository
import com.example.weather.util.WidgetContent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class WeatherWidget : AppWidgetProvider() {

    @Inject
    lateinit var repository: WeatherRepository
    private val job = SupervisorJob()
    private val coroutineScope = CoroutineScope(Dispatchers.IO + job)

    override fun onDisabled(context: Context?) {
        super.onDisabled(context)
        job.cancel()
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)

        coroutineScope.launch {
            val data = repository.getDataForWidget()
            val appWidgetManager = AppWidgetManager.getInstance(context)
            val man = AppWidgetManager.getInstance(context)
            val ids =
                man.getAppWidgetIds(context?.let { ComponentName(it, WeatherWidget::class.java) })

            if (data != null && context != null) {
                for (appWidgetId in ids) {
                    updateAppWidget(
                        context, appWidgetManager, appWidgetId, data
                    )
                }

            }
        }
    }
}

internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int,
    content: WidgetContent
) {
    val views = RemoteViews(context.packageName, R.layout.weather_widget)
    views.setTextViewText(R.id.temp_widget_tv, content.temperature.toString()+"°")
    views.setTextViewText(R.id.date_widget_tv, content.date)
    views.setTextViewText(R.id.territory_name_tv, content.territoryName)
    views.setImageViewBitmap(R.id.condition_icon_widget, content.icon)

    views.setOnClickPendingIntent(R.id.widget_root,
        getPendingIntentActivity(context))

    appWidgetManager.updateAppWidget(appWidgetId, views)
}

private fun getPendingIntentActivity(context: Context): PendingIntent
{
    val intent = Intent(context, MainActivity::class.java)
    return PendingIntent.getActivity(context, 0, intent, 0)
}