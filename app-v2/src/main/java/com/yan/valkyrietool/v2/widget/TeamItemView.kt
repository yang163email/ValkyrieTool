package com.yan.valkyrietool.v2.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import com.yan.valkyrietool.v2.R
import com.yan.valkyrietool.v2.bean.Team
import com.yan.valkyrietool.v2.utils.glide
import kotlinx.android.synthetic.main.item_team.view.*
import org.jetbrains.anko.toast

/**
 *  @author : yan
 *  @date   : 2018/7/3 17:32
 *  @desc   : 队伍item
 */
class TeamItemView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {

    init {
        LayoutInflater.from(context).inflate(R.layout.item_team, this)
    }

    fun bindData(bean: Team) {
        tv_desc.text = bean.desc
        if (bean.isSelect) {
            ll_root.setBackgroundColor(resources.getColor(R.color.c_50f51919))
        } else {
            ll_root.setBackgroundColor(resources.getColor(R.color.c_ffffff))
        }

        val characters = bean.characters
        if (characters.size != 5) {
            //没有5个角色
            context.toast("没有5个角色")
            return
        }
        tv_name1.text = characters[0].name
        tv_name2.text = characters[1].name
        tv_name3.text = characters[2].name
        tv_name4.text = characters[3].name
        tv_name5.text = characters[4].name

        iv_avatar1.glide(characters[0].avatar)
        iv_avatar2.glide(characters[1].avatar)
        iv_avatar3.glide(characters[2].avatar)
        iv_avatar4.glide(characters[3].avatar)
        iv_avatar5.glide(characters[4].avatar)
    }
}