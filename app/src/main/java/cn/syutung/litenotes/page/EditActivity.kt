package cn.syutung.litenotes.page

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.RemoteViews
import cn.syutung.litenotes.R
import cn.syutung.litenotes.bigwidget.getDatePoor
import cn.syutung.litenotes.datebase.TodoDatebase
import cn.syutung.litenotes.datebase.TypeDatebase
import cn.syutung.litenotes.empty.TodosEmpty
import cn.syutung.litenotes.listener.TypeItemSelectedListener
import cn.syutung.litenotes.util.Global
import cn.syutung.litenotes.util.Utils
import kotlinx.android.synthetic.main.activity_edit.*
import kotlinx.android.synthetic.main.activity_edit.add
import kotlinx.android.synthetic.main.activity_edit.cycle_linearlayout
import kotlinx.android.synthetic.main.activity_edit.cycle_picker
import kotlinx.android.synthetic.main.activity_edit.date
import kotlinx.android.synthetic.main.activity_edit.date_picker
import kotlinx.android.synthetic.main.activity_edit.text
import kotlinx.android.synthetic.main.activity_edit.type_picker
import java.text.SimpleDateFormat
import java.util.*
import kotlin.concurrent.thread

class EditActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    var cycleType = 0
    var onClick = false
    var todosEmpty:TodosEmpty?  = null
    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        var id = Global.now
        Utils.setOrdinaryToolBar(this)

        var newList = Global.typeEmpties.map{ it.text }
        val adapter: ArrayAdapter<String> =
            ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, newList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        type_picker.adapter = adapter
        type_picker.onItemSelectedListener = TypeItemSelectedListener()
        cycle_picker.onItemSelectedListener = this@EditActivity

        thread {
            println(id)
            val p = TodoDatebase.getInstance(this)?.todoDao?.getbyId(id)
            val m = p?.let { TypeDatebase.getInstance(this)?.typeDao?.getbyId(it.typeId) }
            println(p.toString())
            runOnUiThread {
                todosEmpty = p
                text.setText(p?.text)
                val sdf = SimpleDateFormat("yyyy-MM-dd")
                date_picker.text = sdf.format(p?.date?.time)
                type_picker.setSelection(newList.indexOf(m?.text))
                if (p?.cycleType == 3){
                    cycle_linearlayout.visibility = View.GONE
                }else{
                    cycle_linearlayout.visibility = View.VISIBLE

                }
                p?.cycleType?.let { cycle_picker.setSelection(it) }
                cycle_time2.setText(p?.cycleTime.toString())

            }


        }


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

        datePicker.init(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(
            Calendar.DAY_OF_MONTH),
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
            if (cycle_time2.text.toString()!=""){
                m = cycle_time2.text.toString().toInt()
            }
            todosEmpty?.text = text.text.toString()
            todosEmpty?.date = calendar
            todosEmpty?.typeId =  Global.typeEmpty.id
            todosEmpty?.cycleType = cycleType
            todosEmpty?.cycleTime = m
            thread {
                TodoDatebase.getInstance(this)?.todoDao?.update(
                    todosEmpty!!
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