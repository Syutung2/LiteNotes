package cn.syutung.litenotes.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import cn.syutung.litenotes.R
import cn.syutung.litenotes.datebase.TodoDatebase
import cn.syutung.litenotes.datebase.TypeDatebase
import cn.syutung.litenotes.empty.TypeEmpty
import cn.syutung.litenotes.page.TypeAddActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import kotlin.concurrent.thread

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [TypeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TypeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var myinflater: LayoutInflater
    private lateinit var views: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    fun loadTypesList(linearLayout: LinearLayout, inflater: LayoutInflater, date:List<TypeEmpty>){
        linearLayout.removeAllViews()
        for (p in date){
            var view = inflater.inflate(
                R.layout.menuitem,
                linearLayout, false)
            view.findViewById<TextView>(R.id.menu_text).text = p.text

            view.setOnLongClickListener{
                showAlertDialog(view, linearLayout,p)
                return@setOnLongClickListener true
            }

            linearLayout.addView(view)
        }
    }
    private fun showAlertDialog(view : View, linearLayout: LinearLayout, todos: TypeEmpty){
        var builder= MaterialAlertDialogBuilder(requireContext())
        builder.setTitle("提示")
        builder.setMessage("是否要删除这个分类么？")
        builder.setPositiveButton("确认"){dialog, which ->
            thread {
                val om = TodoDatebase.getInstance(view.context)?.todoDao?.getbyTypeId(todos.id)
                println(om)
                if (om != null) {
                    if (om.isNotEmpty()) {
                        activity?.runOnUiThread {
                            showDialog(view)
                        }


                    }else{

                        activity?.runOnUiThread {
                            linearLayout.removeView(view)
                        }


                        TypeDatebase.getInstance(view.context)?.typeDao?.delete(todos)


                    }
                }
            }


        }
        builder.setNegativeButton("取消"){dialog, which ->


        }
        var dialog: AlertDialog =builder.create()
        if (!dialog.isShowing) {
            dialog.show()
        }
    }
    private fun showDialog(view : View){
        var builder= MaterialAlertDialogBuilder(requireContext())
        builder.setTitle("提示")
        builder.setMessage("该分类下还有待办哦")
        builder.setPositiveButton("确认"){dialog, which ->

        }

        var dialog: AlertDialog =builder.create()
        if (!dialog.isShowing) {
            dialog.show()
        }
    }
    fun loadTypes(inflater: LayoutInflater){
        thread {
            var p = TypeDatebase.getInstance(requireContext())?.typeDao?.getAll()
            var m = views.findViewById<LinearLayout>(R.id.ty_lists)
            activity?.runOnUiThread {
                if (p != null) {
                    loadTypesList(m!!,inflater,p)
                }
            }

        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        myinflater = inflater
        views = inflater.inflate(R.layout.fragment_type, container, false)
        loadTypes(myinflater)

        views.findViewById<ExtendedFloatingActionButton>(R.id.add_type).setOnClickListener {
            startActivity(Intent(requireContext(), TypeAddActivity::class.java))

        }
        return views
    }

    override fun onResume() {
        super.onResume()
        loadTypes(myinflater)

    }

}