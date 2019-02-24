package com.yan.valkyrietool.widget

import android.content.Context
import android.content.Intent
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import com.bumptech.glide.Glide
import com.yan.valkyrietool.R
import com.yan.valkyrietool.bean.TotalPartBean
import com.yan.valkyrietool.ui.activity.ArrivalDetailActivity
import kotlinx.android.synthetic.main.item_sub_main.view.*

/**
 *  @author : yan
 *  @date   : 2018/6/26 15:50
 *  @desc   : 主界面子item
 */
class MainSubItemView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr), View.OnClickListener {

    init {
        LayoutInflater.from(context).inflate(R.layout.item_sub_main, this)
        setOnClickListener(this)
    }

    private var bean: TotalPartBean? = null

    fun bindData(bean: TotalPartBean) {
        this.bean = bean
        Glide.with(context).load(bean.imgUrl)
                .into(iv)

        tv_desc.text = bean.desc
    }

    override fun onClick(v: View?) {
        bean?.let {
            val intent = Intent(context, ArrivalDetailActivity::class.java)
            intent.putExtra("id", it.id)
            context.startActivity(intent)
        }
    }
}