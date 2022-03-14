package cn.syutung.litenotes.widgets

import android.app.Activity
import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import cn.syutung.litenotes.R
import cn.syutung.litenotes.databinding.CountdownDaysWidgetConfigureBinding
import cn.syutung.litenotes.datebase.TodoDatebase
import cn.syutung.litenotes.empty.TodosEmpty
import java.util.*
import kotlin.concurrent.thread

/**
 * The configuration screen for the [CountdownDaysWidget] AppWidget.
 */
class CountdownDaysWidgetConfigureActivity : Activity() {
    private var appWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID
    private lateinit var appWidgetText: EditText

    private lateinit var binding: CountdownDaysWidgetConfigureBinding
    fun loadDatebace(inflater: LayoutInflater){
        thread {
            var p = TodoDatebase.getInstance(this)?.todoDao?.getAll()
            var m = findViewById<LinearLayout>(R.id.widgets_todos)
            runOnUiThread {
                if (p != null) {
                    loadList(m!!,inflater,p)
                }
            }

        }
    }
    fun loadList(linearLayout: LinearLayout, inflater: LayoutInflater, date:List<TodosEmpty>){
        linearLayout.removeAllViews()
        var calendar = Calendar.getInstance();

        for (p in date){
            var view = inflater.inflate(
                R.layout.todoitem,
                linearLayout, false)
            view.findViewById<TextView>(R.id.item_text).text = p.text
            val cha = getDatePoor(p.date,calendar,p)
            if (p.date.after(calendar)){
                view.findViewById<TextView>(R.id.item_data).text = "还剩 ${cha.toString()} 天"
            }else{
                view.findViewById<TextView>(R.id.item_data).text = "已过去 ${cha.toString()} 天"

            }
            view.setOnClickListener {
                val context = this

                // When the button is clicked, store the string locally
                saveTitlePref(context, appWidgetId, p.id)

                // It is the responsibility of the configuration activity to update the app widget
                val appWidgetManager = AppWidgetManager.getInstance(context)
                updateAppWidget(
                    context,
                    appWidgetManager,
                    appWidgetId
                )

                // Make sure we pass back the original appWidgetId
                val resultValue = Intent()
                resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
                setResult(RESULT_OK, resultValue)
                finish()
            }

            linearLayout.addView(view)
        }
    }
    fun getDatePoor(endDate  : Calendar, nowDate : Calendar, todosEmpty : TodosEmpty) : Int
    {
        var m = Math.abs((endDate.timeInMillis - nowDate.timeInMillis) / (1000 * 3600 * 24)).toInt()
        if (m == 0){

            if (todosEmpty.cycleType == 0){
                endDate.add(Calendar.DAY_OF_MONTH,todosEmpty.cycleTime)
            }else if (todosEmpty.cycleType == 1){
                endDate.add(Calendar.MONTH,todosEmpty.cycleTime)
            }else if (todosEmpty.cycleType == 2){
                endDate.add(Calendar.YEAR,todosEmpty.cycleTime)
            }

            todosEmpty.date = endDate
            thread {
                TodoDatebase.getInstance(this)?.todoDao?.update(todosEmpty)
            }
        }
        return Math.abs((endDate.timeInMillis - nowDate.timeInMillis) / (1000 * 3600 * 24)).toInt()

    }
    public override fun onCreate(icicle: Bundle?) {
        super.onCreate(icicle)

        // Set the result to CANCELED.  This will cause the widget host to cancel
        // out of the widget placement if the user presses the back button.
        setResult(RESULT_CANCELED)

        binding = CountdownDaysWidgetConfigureBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadDatebace(layoutInflater)


        // Find the widget id from the intent.
        val intent = intent
        val extras = intent.extras
        if (extras != null) {
            appWidgetId = extras.getInt(
                AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID
            )
        }

        // If this activity was started with an intent without an app widget ID, finish with an error.
        if (appWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish()
            return
        }

    }

}

private const val PREFS_NAME = "cn.syutung.litenotes.widgets.CountdownDaysWidget"
private const val PREF_PREFIX_KEY = "appwidget_"

// Write the prefix to the SharedPreferences object for this widget
internal fun saveTitlePref(context: Context, appWidgetId: Int, text: Int) {
    val prefs = context.getSharedPreferences(PREFS_NAME, 0).edit()
    prefs.putInt(PREF_PREFIX_KEY + appWidgetId, text)
    prefs.apply()
}

// Read the prefix from the SharedPreferences object for this widget.
// If there is no preference saved, get the default from a resource
internal fun loadTitlePref(context: Context, appWidgetId: Int): Int {
    val prefs = context.getSharedPreferences(PREFS_NAME, 0)
    val titleValue = prefs.getInt(PREF_PREFIX_KEY + appWidgetId, 0)
    return titleValue
}

internal fun deleteTitlePref(context: Context, appWidgetId: Int) {
    val prefs = context.getSharedPreferences(PREFS_NAME, 0).edit()
    prefs.remove(PREF_PREFIX_KEY + appWidgetId)
    prefs.apply()
}