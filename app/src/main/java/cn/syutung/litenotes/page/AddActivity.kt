package cn.syutung.litenotes.page

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import cn.syutung.litenotes.R
import cn.syutung.litenotes.datebase.TodoDatebase
import cn.syutung.litenotes.empty.CycleType
import cn.syutung.litenotes.empty.TodosEmpty
import cn.syutung.litenotes.empty.TypeEmpty
import cn.syutung.litenotes.listener.TypeItemSelectedListener
import cn.syutung.litenotes.util.Global
import cn.syutung.litenotes.util.Global.typeEmpty
import cn.syutung.litenotes.util.Utils
import kotlinx.android.synthetic.main.activity_add.*
import java.util.*
import kotlin.concurrent.thread


class AddActivity : AppCompatActivity(),AdapterView.OnItemSelectedListener {
    var cycleType = 0
    var onClick = false
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        Utils.setOrdinaryToolBar(this)

        var newList = Global.typeEmpties.map{ it.text }
        val adapter: ArrayAdapter<String> =
            ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, newList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        type_picker.adapter = adapter
        type_picker.onItemSelectedListener = TypeItemSelectedListener()
        cycle_picker.onItemSelectedListener = this@AddActivity


        date_picker.setOnClickListener {
            if (!onClick){
                date.visibility = View.VISIBLE
                onClick = true
            }else{
                date.visibility = View.GONE
                onClick = false
            }
        }
        var date2 = Date()
        var calendar = Calendar.getInstance();
        var datePicker = findViewById<DatePicker>(R.id.date)
        val myinflater = LayoutInflater.from(this)

        datePicker.init(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH),
            DatePicker.OnDateChangedListener { datePicker, i, i2, i3 ->
                run {
                    date_picker.text =  "${i}年" + i2 + "月" + i3 + "日"
                    calendar.set(i,i2,i3)
                    date.visibility = View.GONE

                }
            })
        add.setOnClickListener {
            println(date.toString())
            println(date_picker.text)
            var m = 0
            if (cycle_time.text.toString()!=""){
                m = cycle_time.text.toString().toInt()
            }
            thread {
                TodoDatebase.getInstance(this)?.todoDao?.insert(
                    TodosEmpty(
                        text = text.text.toString(),calendar,typeEmpty.id,cycleType,m
                    )
                )
            }
            finish()
        }
    }
    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        println(p1.toString())

            if (p2 == 3){
                cycle_linearlayout.visibility = View.GONE
            }else{
                cycle_linearlayout.visibility = View.VISIBLE

            }
            cycleType = p2
            println(cycleType)


    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }


}

