package com.yan.valkyrietool.v2.ui.activity

import android.graphics.Bitmap
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.webkit.WebView
import android.webkit.WebViewClient
import com.yan.valkyrietool.v2.R
import com.yan.valkyrietool.v2.utils.startLoading
import com.yan.valkyrietool.v2.utils.stopLoading
import com.yan.webdata.bean.MainPartBean
import kotlinx.android.synthetic.main.activity_arrival_detail.*
import kotlinx.android.synthetic.main.layout_loading.*
import org.jetbrains.anko.startActivity

/**
 *  @author : yan
 *  @date   : 2018/7/2 18:16
 *  @desc   : 降临详情界面
 */
class ArrivalDetailActivity : AppCompatActivity(), Toolbar.OnMenuItemClickListener {

    private var bean: MainPartBean? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_arrival_detail)

        bean = intent.getParcelableExtra("bean")
        val settings = web_view.settings
        settings.javaScriptEnabled = true

        toolbar.apply {
            title = bean?.name
            inflateMenu(R.menu.activity_detail_menu)
            setOnMenuItemClickListener(this@ArrivalDetailActivity)
        }
        web_view.webViewClient = object : WebViewClient() {

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                iv_header_loading.startLoading()
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                iv_header_loading.stopLoading()
            }
        }
        web_view.loadUrl(bean?.link)
    }

    override fun onMenuItemClick(item: MenuItem): Boolean {
        if (item.itemId == R.id.recommend_team) {
            //推荐队伍
            startActivity<ArrivalTeamActivity>("bean" to bean)
        }

        return true
    }
}