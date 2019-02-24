package com.yan.valkyrietool.bean

/**
 *  @author : yan
 *  @date   : 2018/6/27 11:28
 *  @desc   : 队伍bean
 */
data class Team(
        val characters: ArrayList<Character>,   //5个角色
        val desc: String = ""    //描述说明
)