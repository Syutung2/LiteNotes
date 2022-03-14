package cn.syutung.litenotes.listener

import android.view.View
import android.widget.AdapterView
import cn.syutung.litenotes.util.Global
import cn.syutung.litenotes.util.Global.typeEmpty

class TypeItemSelectedListener:AdapterView.OnItemSelectedListener {
    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        val m = Global.typeEmpties[p2]
        println(m.toString())
        typeEmpty = m

    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}