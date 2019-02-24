package com.yan.valkyrietool.v2.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.yan.valkyrietool.v2.BuildConfig
import com.yan.valkyrietool.v2.R
import kotlinx.android.synthetic.main.activity_setting.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

/**
 *  @author : yan
 *  @date   : 2018/7/7 11:19
 *  @desc   : 设置界面
 */
class SettingActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        initView()
        tv_version.text = BuildConfig.VERSION_NAME
    }

    private fun initView() {
        tv_personal_characters.setOnClickListener(this)
        tv_check_version.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            tv_personal_characters -> startActivity<PersonalCharactersActivity>()
            tv_check_version -> {
                toast("暂未开放")
            }
        }
    }
}