package com.yan.valkyrietool.bean

/**
 *  @author : yan
 *  @date   : 2018/6/27 11:26
 *  @desc   : 角色bean
 */
data class Character(
        val shortName: String,  //简称---如：诸神黄昏
        val fullName: String,   //全称---如：毁灭龙神诸神黄昏
        val avatar: String,     //头像，图片url或者资源id
        val site: Int   //站位---前中后
)