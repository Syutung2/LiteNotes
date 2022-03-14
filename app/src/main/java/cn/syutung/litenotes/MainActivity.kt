package cn.syutung.litenotes

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import cn.syutung.litenotes.datebase.TodoDatebase
import cn.syutung.litenotes.datebase.TypeDatebase
import cn.syutung.litenotes.empty.TodosEmpty
import cn.syutung.litenotes.empty.TypeEmpty
import cn.syutung.litenotes.fragment.HomeFragment
import cn.syutung.litenotes.fragment.MineFragment
import cn.syutung.litenotes.fragment.TypeFragment
import cn.syutung.litenotes.fragment.WidgetsFragment
import cn.syutung.litenotes.page.*
import cn.syutung.litenotes.util.Global
import cn.syutung.litenotes.util.Utils
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.miui.zeus.mimo.sdk.MimoSdk
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Math.abs
import java.util.*
import kotlin.concurrent.thread


class MainActivity : AppCompatActivity() {
    lateinit var myinflater : LayoutInflater
    lateinit var drawer  :DrawerLayout
    var now = -1;

    override fun onBackPressed() {
        // Move the task containing the MainActivity to the back of the activity stack, instead of
        // destroying it. Therefore, MainActivity will be shown when the user switches back to the app.
        moveTaskToBack(true)
    }

    private fun replaceFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.fragment,fragment)
        transaction.commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        myinflater = LayoutInflater.from(this)
        Utils.setOrdinaryToolBar(this)
        val permissions : () -> String = {Manifest.permission.READ_PHONE_STATE}
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(this,Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            }
        }

        MimoSdk.init(this)

        val share = getSharedPreferences(Global.SHARE_TAG,0)
        val isFirst = share.getBoolean("isFirst",false)
        if (!isFirst){
            thread {
                val p = TypeDatebase.getInstance(this)?.typeDao
                var r = ArrayList<TypeEmpty>()
                r.add(TypeEmpty("工作"))
                r.add(TypeEmpty("学习"))
                p?.insertTypeEmptysAndFriends(TypeEmpty("生活"),r)
                runOnUiThread {
                    share.edit().putBoolean("isFirst",true).apply()
                }

            }
        }

        val isPrivicy = share.getBoolean("isPrivicy",false)
        if (!isPrivicy){
            startActivity(Intent(this,PrivatyActivity::class.java))
        }


        replaceFragment(HomeFragment())
        bottom_navigation.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.home -> {
                    replaceFragment(HomeFragment())
                    true
                }
                R.id.type -> {
                    replaceFragment(TypeFragment())
                    true
                }
                R.id.mine -> {
                    replaceFragment(MineFragment())

                    true
                }
                R.id.widget -> {
                    replaceFragment(WidgetsFragment())

                    true
                }
                else -> {
                    false
                }
            }
        }

    }



    override fun onRestart() {
        super.onRestart()


    }

}
