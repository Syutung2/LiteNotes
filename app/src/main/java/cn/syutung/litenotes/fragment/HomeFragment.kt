package cn.syutung.litenotes.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.view.get
import cn.syutung.litenotes.R
import cn.syutung.litenotes.datebase.TodoDatebase
import cn.syutung.litenotes.datebase.TypeDatebase
import cn.syutung.litenotes.empty.TodosEmpty
import cn.syutung.litenotes.empty.TypeEmpty
import cn.syutung.litenotes.page.AddActivity
import cn.syutung.litenotes.page.DayActivity
import cn.syutung.litenotes.util.Global
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.concurrent.thread

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    private lateinit var myinflater: LayoutInflater
    private lateinit var views: View
    private lateinit var typelists : LinearLayout
    var now = -1;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onResume() {
        super.onResume()
        loadTypes(myinflater)
        loadDatebace(myinflater)
    }
    fun loadTypes(inflater: LayoutInflater){
        thread {
            var p = TypeDatebase.getInstance(requireContext())?.typeDao?.getAll()
            var m = views.findViewById<LinearLayout>(R.id.typelists)
            activity?.runOnUiThread {
                if (p != null) {
                    loadTypesList(m!!,inflater,p)
                }
                Global.typeEmpties = p
            }

        }
    }
    fun loadTypesList(linearLayout: LinearLayout, inflater: LayoutInflater, date:List<TypeEmpty>){
        linearLayout.removeAllViews()
        for (p in date){
            var view = inflater.inflate(
                R.layout.typeitem,
                linearLayout, false)
            view.findViewById<TextView>(R.id.item_text).text = p.text

            view.setOnClickListener {
                val mid = date.indexOf(p)
                if (
                    now!= mid
                ){
                    if (now == -1){
                        views.findViewById<LinearLayout>(R.id.alllinerlayout).setBackgroundResource(R.drawable.s3)
                    }else{
                        typelists[now].setBackgroundResource(R.drawable.s3)
                    }

                    loadDatebacebyType(myinflater,p.id)
                    now = mid
                    typelists[now].setBackgroundResource(R.drawable.s4)


                }

            }

            linearLayout.addView(view)
        }
    }
    fun loadDatebacebyType(inflater: LayoutInflater,type:Int){
        thread {
            var p = TodoDatebase.getInstance(requireContext())?.todoDao?.getbyTypeId(type)
            var m = views.findViewById<LinearLayout>(R.id.todo_listhome)
            activity?.runOnUiThread {
                if (p != null) {
                    loadList(m!!,inflater,p)
                }
            }

        }
    }
    fun loadDatebace(inflater: LayoutInflater){
        thread {
            var p = TodoDatebase.getInstance(requireContext())?.todoDao?.getAll()
            var m = views.findViewById<LinearLayout>(R.id.todo_listhome)
            activity?.runOnUiThread {
                if (p != null) {
                    loadList(m!!,inflater,p)
                }
            }

        }
    }
    @SuppressLint("SetTextI18n")
    fun loadList(linearLayout: LinearLayout, inflater: LayoutInflater, date:List<TodosEmpty>){
        linearLayout.removeAllViews()
        var calendar = Calendar.getInstance();
        if (date.isEmpty()|| date.size == 0){
            views.findViewById<TextView>(R.id.nulltext).visibility=View.VISIBLE
        }else{
            views.findViewById<TextView>(R.id.nulltext).visibility=View.GONE

        }
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
                val intent = Intent(requireContext(), DayActivity::class.java)
                intent.putExtra("id",p.id)
                intent.putExtra("text",p.text)
                if (p.date.after(calendar)){
                    intent.putExtra("tishi", "还剩 ${cha.toString()} 天")
                }else{
                    intent.putExtra("tishi", "已过去 ${cha.toString()} 天")
                }
                intent.putExtra("cycleType",p.cycleType)
                intent.putExtra("cycleTime",p.cycleTime)
                val year = p.date.get(Calendar.YEAR)
                val month = p.date.get(Calendar.MONTH)
                val day = p.date.get(Calendar.DAY_OF_MONTH)
                intent.putExtra("year",year)
                intent.putExtra("month",month)
                intent.putExtra("day",day)


                startActivity(intent)
            }

            view.setOnLongClickListener{

                showAlertDialog(view, linearLayout,p)
                return@setOnLongClickListener true
            }



            linearLayout.addView(view)
        }
    }
    private fun showAlertDialog(view : View, linearLayout: LinearLayout, todosEmpty: TodosEmpty){
        var builder= MaterialAlertDialogBuilder(requireContext())
        builder.setTitle("提示")
        builder.setMessage("是否要删除这个待办么？")
        builder.setPositiveButton("确认"){dialog, which ->
            linearLayout.removeView(view)
            thread {
                TodoDatebase.getInstance(view.context)?.todoDao?.delete(todosEmpty)
            }
        }
        builder.setNegativeButton("取消"){dialog, which ->


        }
        var dialog: AlertDialog =builder.create()
        if (!dialog.isShowing) {
            dialog.show()
        }
    }
    fun getDatePoor(endDate  : Calendar, nowDate : Calendar, todosEmpty : TodosEmpty) : Int {
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
                TodoDatebase.getInstance(requireContext())?.todoDao?.update(todosEmpty)
            }
        }
        return Math.abs((endDate.timeInMillis - nowDate.timeInMillis) / (1000 * 3600 * 24)).toInt()

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        myinflater = inflater
        views = inflater.inflate(R.layout.fragment_home, container, false)
        loadTypes(myinflater)
        loadDatebace(myinflater)
        typelists = views.findViewById(R.id.typelists)
        views.findViewById<TextView>(R.id.aall).setOnClickListener {
            if (now!=-1){
                typelists[now].setBackgroundResource(R.drawable.s3)
                loadDatebace(myinflater)
                now = -1
                views.findViewById<LinearLayout>(R.id.alllinerlayout).setBackgroundResource(R.drawable.s4)
            }
        }
        views.findViewById<ExtendedFloatingActionButton>(R.id.new_todo).setOnClickListener {
            startActivity(
                Intent(
                    requireContext(), AddActivity::class.java
                )
            )
        }
        // Inflate the layout for this fragment
        return views
    }

}