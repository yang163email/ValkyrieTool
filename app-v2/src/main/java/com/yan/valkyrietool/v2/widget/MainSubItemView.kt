package com.yan.valkyrietool.v2.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import com.yan.valkyrietool.v2.R
import com.yan.valkyrietool.v2.ui.activity.ArrivalDetailActivity
import com.yan.valkyrietool.v2.utils.glide
import com.yan.webdata.bean.MainPartBean
import kotlinx.android.synthetic.main.item_sub_main.view.*
import org.jetbrains.anko.startActivity

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

    private var bean: MainPartBean? = null

    fun bindData(bean: MainPartBean) {
        this.bean = bean
        iv.glide(bean.imgUrl)

        val name = bean.name.replace("超绝降临", "")
                .replace("超降临", "")
                .replace("降临", "")
                .replace("！", "").trim()
        tv_desc.text = name
    }

    override fun onClick(v: View?) {
        bean?.let {
            context.startActivity<ArrivalDetailActivity>("bean" to bean)
        }
    }
}