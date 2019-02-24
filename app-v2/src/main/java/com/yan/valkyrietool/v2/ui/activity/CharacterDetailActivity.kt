package com.yan.valkyrietool.v2.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.yan.valkyrietool.v2.R
import kotlinx.android.synthetic.main.activity_character_detail.*

/**
 *  @author : yan
 *  @date   : 2018/7/5 15:02
 *  @desc   : 角色详情界面
 */
class CharacterDetailActivity : AppCompatActivity() {

    private var name: String? = null
    private var linkUrl: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character_detail)

        name = intent.getStringExtra("name")
        linkUrl = intent.getStringExtra("linkUrl")

        toolbar.title = name

        val settings = web_view.settings
        settings.javaScriptEnabled = true

        web_view.loadUrl(linkUrl)
    }
}