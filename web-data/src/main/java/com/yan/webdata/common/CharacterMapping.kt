package com.yan.webdata.common

/**
 *  @author       to yan
 *  @date         to 2018/7/4 11 to12
 *  @description  to 人物属性映射表
 */
object CharacterMapping {

    /**
     * 主动技能属性map
     */
    val aSResistanceMap = hashMapOf(
            "0" to "/images/icon_attribute/icon_attribute_00.png",    //无属性
            "1" to "/images/icon_attribute/icon_attribute_01.png",    //火属性
            "2" to "/images/icon_attribute/icon_attribute_02.png",    //水属性
            "3" to "/images/icon_attribute/icon_attribute_03.png",    //木属性
            "4" to "/images/icon_attribute/icon_attribute_04.png",    //光属性
            "5" to "/images/icon_attribute/icon_attribute_05.png",    //暗属性
            "6" to ""                                                 //无伤害
    )

    /**
     * 主动技能属性(觉醒)map
     */
    val awakeningASResistanceMap = hashMapOf(
            "1" to "<img class='filter_attribute-img' src='/images/icon_attribute/icon_attribute_01.png'>",
            "2" to "<img class='filter_attribute-img' src='/images/icon_attribute/icon_attribute_02.png'>",
            "3" to "<img class='filter_attribute-img' src='/images/icon_attribute/icon_attribute_03.png'>",
            "4" to "<img class='filter_attribute-img' src='/images/icon_attribute/icon_attribute_04.png'>",
            "5" to "<img class='filter_attribute-img' src='/images/icon_attribute/icon_attribute_05.png'>"
    )

    /**
     * 种族map
     */
    val raceMap = hashMapOf(
            "1" to "神族",
            "2" to "人类",
            "3" to "精灵",
            "4" to "矮人",
            "5" to "亚人",
            "6" to "巨人",
            "7" to "神兽"
    )

    /**
     * 初始星级map
     */
    val initStarMap = hashMapOf(
            "1" to "1☆",
            "2" to "2☆",
            "3" to "3☆"
    )

    /**
     * 性别map
     */
    val genderMap = hashMapOf(
            "1" to "♂",
            "2" to "♀",
            "3" to "???"
    )

    /**
     * 属性抗性map
     */
    val attrResistanceMap = hashMapOf(
            "revision_attr_fire" to "火",
            "revision_attr_water" to "水",
            "revision_attr_light" to "光",
            "revision_attr_earth" to "木",
            "revision_attr_dark" to "暗"
    )
}