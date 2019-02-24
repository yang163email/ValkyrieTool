package com.yan.valkyrietool.v2.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.yan.valkyrietool.v2.bean.Team
import com.yan.valkyrietool.v2.widget.TeamItemView

/**
 *  @author : yan
 *  @date   : 2018/7/3 17:30
 *  @desc   : todo
 */
class TeamAdapter(val itemClick: (Team, Int) -> Unit) : RecyclerView.Adapter<TeamAdapter.TeamHolder>() {

    var dataList = arrayListOf<Team>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamHolder {
        val itemView = TeamItemView(parent.context)
        return TeamHolder(itemView)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: TeamHolder, position: Int) {
        val itemView = holder.itemView as TeamItemView
        itemView.setOnClickListener {
            itemClick(dataList[position], position)
        }
        itemView.bindData(dataList[position])
    }

    fun resetSelect() {
        dataList.forEach {
            it.isSelect = false
        }
        notifyDataSetChanged()
    }

    fun selectAll() {
        dataList.forEach {
            it.isSelect = true
        }
        notifyDataSetChanged()
    }

    class TeamHolder(itemView: View?) : RecyclerView.ViewHolder(itemView)
}