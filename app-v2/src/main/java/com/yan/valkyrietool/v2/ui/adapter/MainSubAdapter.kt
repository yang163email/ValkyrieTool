package com.yan.valkyrietool.v2.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.yan.valkyrietool.v2.widget.MainSubItemView
import com.yan.webdata.bean.MainPartBean

/**
 *  @author : yan
 *  @date   : 2018/6/26 17:12
 *  @desc   : 主页面RV第二层adapter
 */
class MainSubAdapter : RecyclerView.Adapter<MainSubAdapter.MainSubHolder>() {

    var subDataList = arrayListOf<MainPartBean>()
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