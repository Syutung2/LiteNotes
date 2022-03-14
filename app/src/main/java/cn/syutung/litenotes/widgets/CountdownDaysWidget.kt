package cn.syutung.litenotes.widgets

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.widget.RemoteViews
import cn.syutung.litenotes.R
import cn.syutung.litenotes.datebase.TodoDatebase
import cn.syutung.litenotes.empty.TodosEmpty
import java.text.SimpleDateFormat
import java.util.*
import kotlin.concurrent.thread

/**
 * Implementation of App Widget functionality.
 * App Widget Configuration implemented in [CountdownDaysWidgetConfigureActivity]
 */
class CountdownDaysWidget : AppWidgetProvider() {
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onDeleted(context: Context, appWidgetIds: IntArray) {
        // When the user deletes the widget, delete the preference associated with it.
        for (appWidgetId in appWidgetIds) {
            deleteTitlePref(context, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

internal fun getDatePoor(endDate  : Calendar, nowDate : Calendar, todosEmpty : TodosEmpty) : Int
{
    var m = Math.abs((endDate.timeInMillis - nowDate.timeInMillis) / (1000 * 3600 * 24)).toInt()
    return m

}
internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int
) {
    val widgetText = loadTitlePref(context, appWidgetId)
    println(widgetText)
    thread {
        var p = TodoDatebase.getInstance(context)?.todoDao?.getbyId(widgetText)

        // Construct the RemoteViews object
        val views = RemoteViews(context.packageName, R.layout.countdown_days_widget)
        if (p != null) {
            var calendar = Calendar.getInstance();
            val sdf = SimpleDateFormat("yyyy-MM-dd")
            val cha = getDatePoor(p.date,calendar,p).toString()
            views.setTextViewText(R.id.widgets_text, p.text)
            if (p.date.after(calendar)){
                views.setTextViewText(R.id.widgets_date, "还剩 ${cha.toString()} 天")
            }else{
                views.setTextViewText(R.id.widgets_date, "已过去 ${cha.toString()} 天")
            }

            views.setTextViewText(R.id.widgets_now, sdf.format(p.date.time))

        }
        appWidgetManager.updateAppWidget(appWidgetId, views)

    }


}

