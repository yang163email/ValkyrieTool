package com.yan.valkyrietool.v2.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yan.valkyrietool.v2.R
import com.yan.valkyrietool.v2.utils.glide
import com.yan.webdata.bean.Character
import kotlinx.android.synthetic.main.item_add_team.view.*

/**
 *  @author : yan
 *  @date   : 2018/7/4 17:01
 *  @desc   : 添加队伍rv的adapter
 */
class AddTeamAdapter(private val itemClick: (Character, Int) -> Unit, private val itemLongClick: (Character) -> Unit)
    : RecyclerView.Adapter<AddTeamAdapter.AddTeamHolder>() {

    var dataList = arrayListOf<Character>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddTeamHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_add_team, parent, false)
        return AddTeamHolder(itemView)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: AddTeamHolder, position: Int) {
        holder.itemView.setOnClickListener {
            itemClick(dataList[position], position)
            notifyItemChanged(position)
        }
        holder.itemView.setOnLongClickListener {
            itemLongClick(dataList[position])
            true
        }
        holder.bindData(dataList[position])
    }

    class AddTeamHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

        fun bindData(character: Character) {
            itemView.apply {
                tv_name.text = character.name
                iv_avatar.glide(character.avatar)

                iv_ok.visibility = if (character.isSelect) View.VISIBLE else View.GONE
            }
        }
    }
}