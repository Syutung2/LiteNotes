package cn.syutung.litenotes.page

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import cn.syutung.litenotes.R
import cn.syutung.litenotes.datebase.TodoDatebase
import cn.syutung.litenotes.datebase.TypeDatebase
import cn.syutung.litenotes.empty.TodosEmpty
import cn.syutung.litenotes.empty.TypeEmpty
import cn.syutung.litenotes.util.Global
import cn.syutung.litenotes.util.Utils
import kotlinx.android.synthetic.main.activity_add.*
import kotlinx.android.synthetic.main.activity_type_add.*
import kotlinx.android.synthetic.main.activity_type_add.add
import kotlinx.android.synthetic.main.activity_type_add.text
import kotlin.concurrent.thread

class TypeAddActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_type_add)
        Utils.setOrdinaryToolBar(this)

        add.setOnClickListener {
            var text = text.text.toString()
            if (text == ""){
                Toast.makeText(this,"你还没有输入哦",Toast.LENGTH_SHORT).show()
            }else{
                thread {
                    TypeDatebase.getInstance(this)?.typeDao?.insert(
                        TypeEmpty(
                            text = text
                        )
                    )
                }
                finish()
            }
        }
    }
}