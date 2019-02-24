package com.yan.valkyrietool.bean

/**
 *  @author : yan
 *  @date   : 2018/6/27 11:35
 *  @desc   : 降临boss bean
 */
data class ArrivalBoss(
        val id: String, //唯一id，用于查询
        val simpleName: String,  //简称---如：诸神黄昏
        val fullName: String,   //全称---如：毁灭龙神诸神黄昏
        val avatar: String, //头像，图片url或者资源id
        val level: Int,  //降临类型---超绝、超、普通
        val star: Int,  //星级---1-8、s1-s3
        val starImgUrl: String,     //星星图片url
        val site: Int,  //站位---前中后
        val activeSkillName: String,    //主动技能名称
        val activeSkillDesc: String,    //主动技能描述
        val autoSkillDesc: String,  //自动技能描述
        val fireResistance: Float,  //火抗
        val waterResistance: Float, //水抗
        val woodResistance: Float,  //木抗
        val lightResistance: Float, //光抗
        val shadowResistance: Float,    //暗抗
        val otherEnemies: ArrayList<OtherEnemy>,    //其他敌人
        val recommendTeam: Team, //推荐队伍
        var resistance: String = "${fireResistance}_${waterResistance}_${woodResistance}_${lightResistance}_${shadowResistance}" //属性抗性(0-4,0最低,4最高)---火水木光暗，如：1.0_3.4_2.0_1.0_3.0
)