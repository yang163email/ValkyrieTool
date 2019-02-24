package com.yan.valkyrietool.bean

/**
 *  @author : yan
 *  @date   : 2018/6/26 15:44
 *  @desc   : 总降临数据bean
 */
data class TotalArrivalBean(
        val partList: ArrayList<TotalPartBean>,
        val type: Int       //类型：1：超绝降临；2：超降临；3：降临
)

data class TotalPartBean(
        val id: String,
        val imgUrl: String, //图片地址
        val desc: String   //名称
)


/*
    降临boss需要绑定的字段(ArrivalBoss)

    1. simpleName(String)：简称---如：诸神黄昏
    2. fullName(String)：全称---如：毁灭龙神诸神黄昏
    3. avatar(String)：头像，图片url或者资源id
    4. type(Int)：降临类型---超绝、超、普通
    5. star(Int)：星级---1-8、s1-s3
    6. site(Int)：站位---前中后
    7. activeSkillName(String)：主动技能名称
    8. activeSkillDesc(String)：主动技能描述
    9. autoSkillDesc(String)：自动技能描述
    10.resistance(String)：属性抗性(0-4,0最低,4最高)---火水木光暗，如：1.0_3.4_2.0_1.0_3.0
    11.fireResistance(Float)：火抗
    12.waterResistance(Float)：水抗
    13.woodResistance(Float)：木抗
    14.lightResistance(Float)：光抗
    15.shadowResistance(Float)：暗抗
    16.otherEnemies(ArrayList<OtherEnemy>)：其他敌人
    17.recommendTeam(Team)：推荐队伍
 */

/*
    其他敌人(OtherEnemy)

    1. simpleName(String)：简称---如：诸神黄昏
    2. fullName(String)：全称---如：毁灭龙神诸神黄昏
    3. avatar(String)：头像，图片url或者资源id
    4. site(Int)：站位---前中后
    5. autoSkillDesc(String)：自动技能描述
    6. resistance(String)：属性抗性(0-4,0最低,4最高)---火水木光暗，如：1.0_3.4_2.0_1.0_3.0
    7. fireResistance(Float)：火抗
    8. waterResistance(Float)：水抗
    9. woodResistance(Float)：木抗
    10.lightResistance(Float)：光抗
    11.shadowResistance(Float)：暗抗
 */

/*
    队伍(Team)

    1. characters(ArrayList<Character>)：5个角色
    2. desc(String)：说明描述
 */

/*
    角色(Character)

    1. simpleName(String)：简称---如：诸神黄昏
    2. fullName(String)：全称---如：毁灭龙神诸神黄昏
    3. avatar(String)：头像，图片url或者资源id
    4. site(Int)：站位---前中后
 */