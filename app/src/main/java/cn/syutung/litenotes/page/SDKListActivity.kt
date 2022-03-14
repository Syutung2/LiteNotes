package cn.syutung.litenotes.page

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import cn.syutung.litenotes.R
import cn.syutung.litenotes.empty.SDKEmpty
import cn.syutung.litenotes.empty.TypeEmpty
import cn.syutung.litenotes.util.Utils
import kotlinx.android.synthetic.main.activity_sdklist.*
import kotlinx.android.synthetic.main.sdkitem.view.*
import java.util.ArrayList
import java.util.zip.Inflater

class SDKListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sdklist)

        Utils.setOrdinaryToolBar(this)
        var inflater =  LayoutInflater.from(this);
        var m = ArrayList<SDKEmpty>()
        m.add(SDKEmpty("Xiaomi Ads SDK","用于提供开屏广告接入,和少部分广告接入","网络权限、网络状态、广告ID"))
        m.add(SDKEmpty("Firebase SDK","用于整体有效数据统计或无效流量鉴别，以便为用户提供更好的服务","设备信息：设备品牌、型号、软件版本等基础信息；网络信息：设备网络连接状态、ip；应用信息：SDK宿主应用包名、版本号等信息；"))
        m.add(SDKEmpty(" Xiaomi 检查更新 SDK","用于应用内检查APP更新","读写本地APP，读取版本号"))


        loadTypesList(findViewById<LinearLayout>(R.id.sdklist),inflater,m)
    }

    fun loadTypesList(linearLayout: LinearLayout, inflater: LayoutInflater, date:List<SDKEmpty>){
        linearLayout.removeAllViews()
        for (p in date){
            var view = inflater.inflate(
                R.layout.sdkitem,
                linearLayout, false)
            view.findViewById<TextView>(R.id.sdk_text).text = p.name
            view.findViewById<TextView>(R.id.sdk_zoys).text = p.fuction
            view.findViewById<TextView>(R.id.sdk_select).text = p.select


            linearLayout.addView(view)
        }
    }
}