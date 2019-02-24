package com.yan.valkyrietool.v2.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.yan.valkyrietool.v2.R
import kotlinx.android.synthetic.main.activity_test.*

/**
 *  @author : yan
 *  @date   : 2018/6/27 17:14
 *  @desc   : todo
 */
class TestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        val settings = web_view.settings
        settings.javaScriptEnabled = true

        web_view.loadUrl("http://jam-capture-vcon-ww.ateamid.com/zh_CN/connect_detail/207005.html")
    }
}