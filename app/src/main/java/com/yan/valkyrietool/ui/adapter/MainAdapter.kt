package com.yan.valkyrietool.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.yan.valkyrietool.bean.TotalArrivalBean
import com.yan.valkyrietool.widget.MainItemView

/**
 *  @author : yan
 *  @date   : 2018/6/26 15:42
 *  @desc   : 主页面RV最外层adapter
 */
class MainAdapter : RecyclerView.Adapter<MainAdapter.MainHolder>() {

    var dataList = arrayListOf<TotalArrivalBean>()
        set(value) {
            field.addAll(value)
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val itemView = MainItemView(parent.context)
        return MainHolder(itemView)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val itemView = holder.itemView as MainItemView
        itemView.bindData(dataList[position])
    }

    class MainHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}