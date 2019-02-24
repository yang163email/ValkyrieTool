package com.yan.webdata.bean

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 *  @author : yan
 *  @date   : 2018/6/27 11:26
 *  @desc   : 角色bean
 */
@Parcelize
data class Character(
        val linkUrl: String,    //详情链接
        val name: String,  //名字---如：毁灭龙神诸神黄昏
        val avatar: String,     //头像，图片url或者资源id
        val site: String,   //站位---前中后
        val newSort: Int,   //最新排序
        val siteSort: Int,  //位置排序
        val race: String,  //种族
        val initStar: String,  //初始星级
        val gender: String,    //性别
        val attrResistance: String, //属性抗性
        val aSAttr: String,  //主动技能属性
        val awakeningASAttr: String,   //觉醒技能属性
        var isSelect: Boolean = false   //是否选中，自定义添加的
) : Parcelable