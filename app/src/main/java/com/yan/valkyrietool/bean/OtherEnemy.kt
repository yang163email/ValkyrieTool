package com.yan.valkyrietool.bean

/**
 *  @author : yan
 *  @date   : 2018/6/27 11:29
 *  @desc   : 其他敌人(水晶)
 */
data class OtherEnemy(
        val shortName: String,   //简称---如：诸神黄昏
        val fullName: String,   //全称---如：毁灭龙神诸神黄昏
        val avatar: String, //头像，图片url或者资源id
        val site: Int,  //站位---前中后
        val autoSkillDesc: String,  //自动技能描述
        val fireResistance: Float,  //火抗
        val waterResistance: Float, //水抗
        val woodResistance: Float,  //木抗
        val lightResistance: Float, //光抗
        val shadowResistance: Float, //暗抗
        var resistance: String = "${fireResistance}_${waterResistance}_${woodResistance}_${lightResistance}_${shadowResistance}" //属性抗性(0-4,0最低,4最高)---火水木光暗，如：1.0_3.4_2.0_1.0_3.0
)