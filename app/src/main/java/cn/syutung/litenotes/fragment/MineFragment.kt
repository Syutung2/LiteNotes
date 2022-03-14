package cn.syutung.litenotes.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import cn.syutung.litenotes.R
import cn.syutung.litenotes.page.PrivatyActivity
import cn.syutung.litenotes.page.SDKListActivity
import cn.syutung.litenotes.page.UserActivity
import cn.syutung.litenotes.util.Global
import com.google.android.material.card.MaterialCardView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.miui.zeus.mimo.sdk.BannerAd
import com.miui.zeus.mimo.sdk.BannerAd.BannerInteractionListener
import com.miui.zeus.mimo.sdk.BannerAd.BannerLoadListener
import com.miui.zeus.mimo.sdk.TemplateAd
import com.xiaomi.market.sdk.XiaomiUpdateAgent


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MineFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MineFragment : Fragment() {
    // TODO: Rename and change types of parameters


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    lateinit var bannerAd : BannerAd
    private fun showchexiaoDialog(){
        var builder= MaterialAlertDialogBuilder(requireContext())
        builder.setTitle("提示")
        builder.setMessage("是否要撤销隐私协议授权？")
        builder.setPositiveButton("确认"){dialog, which ->
            val share = activity?.getSharedPreferences(Global.SHARE_TAG,0)
            share?.edit()?.putBoolean("isPrivicy",false)?.apply()
            activity?.finish()
        }
        builder.setNegativeButton("取消"){dialog, which ->


        }
        var dialog: AlertDialog =builder.create()
        if (!dialog.isShowing) {
            dialog.show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val views = inflater.inflate(R.layout.fragment_mine, container, false)
        views.findViewById<MaterialCardView>(R.id.ysxy).setOnClickListener {
            startActivity(
                Intent(requireContext(),PrivatyActivity::class.java)
            )
        }
        views.findViewById<MaterialCardView>(R.id.cxysxy).setOnClickListener {
            showchexiaoDialog()
        }
        views.findViewById<MaterialCardView>(R.id.yhxy).setOnClickListener {
            startActivity(
                Intent(requireContext(),UserActivity::class.java)
            )
        }
        views.findViewById<MaterialCardView>(R.id.xtsdklist).setOnClickListener {
            startActivity(
                Intent(requireContext(),SDKListActivity::class.java)
            )
        }
        views.findViewById<MaterialCardView>(R.id.jcgx).setOnClickListener {
            XiaomiUpdateAgent.update(requireContext());

        }
        //9b16250f3ec0f1817a79c95c13c71e8f
        //802e356f1726f9ff39c69308bfd6f06a
        bannerAd = BannerAd()
        bannerAd.loadAd("9b16250f3ec0f1817a79c95c13c71e8f", object : BannerLoadListener {
            override fun onBannerAdLoadSuccess() {
                //请求广告成功, 在需要的时候可以展示广告

                //showAd()
            }

            override fun onAdLoadFailed(error: Int, errorMsg: String) {
                //请求广告失败
            }
        })
        bannerAd.showAd(activity, container, object : BannerInteractionListener{
            override fun onAdClick() {
            }

            override fun onAdShow() {
            }

            override fun onAdDismiss() {
            }

            override fun onRenderSuccess() {
            }

            override fun onRenderFail(p0: Int, p1: String?) {
            }

        })

        // Inflate the layout for this fragment
        return views
    }

    override fun onDestroy() {
        super.onDestroy()
        bannerAd.destroy();

    }


}