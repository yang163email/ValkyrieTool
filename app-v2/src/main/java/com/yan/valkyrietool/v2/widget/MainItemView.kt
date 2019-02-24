package com.yan.valkyrietool.v2.widget

import android.content.Context
import android.support.v7.widget.GridLayoutManager
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import com.yan.valkyrietool.v2.R
import com.yan.valkyrietool.v2.ui.adapter.MainSubAdapter
import com.yan.valkyrietool.v2.utils.glide
import com.yan.valkyrietool.v2.widget.rv.SpaceItemDecoration
import com.yan.webdata.bean.MainArrivalBean
import kotlinx.android.synthetic.main.item_main.view.*
import org.jetbrains.anko.dip

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

    fun bindData(bean: MainArrivalBean) {
        iv_header.glide(bean.titleImg)
        tv_header.text = bean.typeName

        val mainSubAdapter = MainSubAdapter()
        recycler_view.apply {
            layoutManager = GridLayoutManager(context, 3)
            addItemDecoration(SpaceItemDecoration(rightSpace = dip(5), bottomSpace = dip(4)))
            adapter = mainSubAdapter
        }
        mainSubAdapter.subDataList = bean.partList
    }
}