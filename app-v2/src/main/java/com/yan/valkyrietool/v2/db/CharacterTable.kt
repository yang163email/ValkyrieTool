package com.yan.valkyrietool.v2.db

/**
 *  @author : yan
 *  @date   : 2018/7/9 10:05
 *  @desc   : 角色表
 */
object CharacterTable {

    const val TABLE_NAME = "personalCharacters"

    /*
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
     */
    const val ID = "_id"

    const val LINK_URL = "linkUrl"
    const val NAME = "name"
    const val AVATAR = "avatar"
    const val SITE = "site"
    const val NEW_SORT = "newSort"
    const val SITE_SORT = "siteSort"
    const val RACE = "race"
    const val INIT_STAR = "initStar"
    const val GENDER = "gender"
    const val ATTR_RESISTANCE = "attrResistance"
    const val AS_ATTR = "aSAttr"
    const val AWAKENING_AS_ATTR = "awakeningASAttr"
}