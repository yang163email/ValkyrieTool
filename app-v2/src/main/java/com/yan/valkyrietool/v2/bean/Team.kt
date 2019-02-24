package com.yan.valkyrietool.v2.bean

import android.os.Parcelable
import com.yan.webdata.bean.Character
import kotlinx.android.parcel.Parcelize

/**
 *  @author : yan
 *  @date   : 2018/6/27 11:28
 *  @desc   : 队伍bean
 */
@Parcelize
data class Team(
        val characters: ArrayList<Character>,   //5个角色
        val desc: String = "无说明",    //描述说明
        var isSelect: Boolean = false   //是否选中
) : Parcelable

data class Teams(
        val linkUrl: String,
        val team: Team
)