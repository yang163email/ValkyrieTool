package com.yan.valkyrietool.widget

import android.content.Context
import android.support.v7.widget.GridLayoutManager
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import com.yan.valkyrietool.R
import com.yan.valkyrietool.bean.TotalArrivalBean
import com.yan.valkyrietool.data.HEADER_TITLE_URL
import com.yan.valkyrietool.repository.BaseArrivalBossInfoFactory
import com.yan.valkyrietool.ui.adapter.MainSubAdapter
import com.yan.valkyrietool.utils.glide
import kotlinx.android.synthetic.main.item_main.view.*

/**
 *  @author : yan
 *  @date   : 2018/6/26 15:50
 *  @desc   : 主界面item
 */
class MainItemView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {

    init {
        LayoutInflater.from(context).inflate(R.layout.item_main, this)
    }

    fun bindData(bean: TotalArrivalBean) {
        iv_header.glide(HEADER_TITLE_URL)
        val type = bean.type
        val headerTitle = when (type) {
            BaseArrivalBossInfoFactory.SUPER2 -> "超绝降临"
            BaseArrivalBossInfoFactory.SUPER -> "超降临"
            BaseArrivalBossInfoFactory.NORMAL -> "降临"
            else -> "降临"
        }
        tv_header.text = headerTitle

        val mainSubAdapter = MainSubAdapter()
        recycler_view.apply {
            layoutManager = GridLayoutManager(context, 4)
            adapter = mainSubAdapter
        }
        mainSubAdapter.subDataList = bean.partList
    }
}