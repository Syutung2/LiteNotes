package cn.syutung.litenotes.page

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import cn.syutung.litenotes.R
import cn.syutung.litenotes.util.Global
import cn.syutung.litenotes.util.Utils
import kotlinx.android.synthetic.main.activity_privaty.*

class PrivatyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_privaty)
        Utils.setOrdinaryToolBar(this)


        litenotes.loadUrl("https://we-chat.cn/ysxy4.html")

        val share = getSharedPreferences(Global.SHARE_TAG,0)
        val isPrivicy = share.getBoolean("isPrivicy",false)
        if (isPrivicy){
            buttom.visibility = View.GONE
            chexiao.visibility = View.VISIBLE
            chexiao.setOnClickListener {
                share.edit().putBoolean("isPrivicy",false).apply()
                finish()
            }
        }else{
            tongyi.setOnClickListener {
                share.edit().putBoolean("isPrivicy",true).apply()
                finish()
            }
            jujue.setOnClickListener {
                finish()
            }
        }


    }
}