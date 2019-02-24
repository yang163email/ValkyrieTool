package com.yan.valkyrietool.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.yan.valkyrietool.bean.TotalPartBean
import com.yan.valkyrietool.widget.MainSubItemView

/**
 *  @author : yan
 *  @date   : 2018/6/26 17:12
 *  @desc   : 主页面RV第二层adapter
 */
class MainSubAdapter : RecyclerView.Adapter<MainSubAdapter.MainSubHolder>() {

    var subDataList = arrayListOf<TotalPartBean>()
        set(value) {
            field.addAll(value)
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainSubHolder {
        val mainSubItemView = MainSubItemView(parent.context)
        return MainSubHolder(mainSubItemView)
    }

    override fun getItemCount(): Int = subDataList.size

    override fun onBindViewHolder(holder: MainSubHolder, position: Int) {
        val itemView = holder.itemView as MainSubItemView
        itemView.bindData(subDataList[position])
    }

    class MainSubHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}