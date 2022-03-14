package cn.syutung.litenotes.page

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import cn.syutung.litenotes.R
import cn.syutung.litenotes.datebase.TodoDatebase
import cn.syutung.litenotes.util.Global
import cn.syutung.litenotes.util.Utils
import kotlinx.android.synthetic.main.activity_day.*
import kotlinx.android.synthetic.main.activity_edit.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.concurrent.thread

class DayActivity : AppCompatActivity() {
    var id = 0;
    fun initdata(){
        thread {
            println(id)
            val p = TodoDatebase.getInstance(this)?.todoDao?.getbyId(id)
            println(p.toString())
            runOnUiThread {
                todo_text.text = p?.text
                var calendar = Calendar.getInstance();
                val sdf = SimpleDateFormat("yyyy-MM-dd")
                val cha = p?.date?.let { cn.syutung.litenotes.widgets.getDatePoor(it, calendar, p).toString() }
                if (p?.date?.after(calendar) == true){
                    todo_date.text = "还剩 ${cha.toString()} 天"
                }else{
                    todo_date.text = "已过去 ${cha.toString()} 天"
                }

                val year = p?.date?.get(Calendar.YEAR)
                val month = p?.date?.get(Calendar.MONTH)
                val day = p?.date?.get(Calendar.DAY_OF_MONTH)
                calendar.set(year!!,month!!,day!!)
                if (p?.cycleType == 0){
                    calendar.add(Calendar.DAY_OF_MONTH,-p?.cycleTime)
                    todo_last.text = sdf.format(calendar.time)
                }else if (p?.cycleType == 1){
                    calendar.add(Calendar.MONTH,-p?.cycleTime)
                    todo_last.text = sdf.format(calendar.time)

                }else if (p?.cycleType == 2){
                    calendar.add(Calendar.YEAR,-p?.cycleTime)
                    todo_last.text = sdf.format(calendar.time)

                }else{
                    todo_last.visibility = View.GONE
                    last_text.visibility = View.GONE
                }

            }


        }

    }

    override fun onRestart() {
        super.onRestart()
        initdata()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_day)
        id = intent.getIntExtra("id",0)
        Utils.setOrdinaryToolBar(this)

        initdata()

        edit.setOnClickListener {
            val i2 = Intent(this,EditActivity::class.java)
            Global.now = id;
            startActivity(i2)
        }


    }
    fun getDatePoor(endDate  : Calendar, nowDate : Calendar) : Int
    {

        return Math.abs((endDate.timeInMillis - nowDate.timeInMillis) / (1000 * 3600 * 24)).toInt()

    }
}


