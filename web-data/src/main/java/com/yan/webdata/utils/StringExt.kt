package com.yan.webdata.utils

import com.yan.webdata.common.DOMAIN

/**
 *  @author      : yan
 *  @date        : 2018/6/29 18:17
 *  @description : TODO
 */
/**
 * 插入字符串
 * 如: aaaabbbb  在第index=3插入c    则得到aaacabbbb
 */
fun String.insert(str: String, index: Int): String {
    return substring(0, index) + str + substring(index until length)
}

fun String.appendDomain(): String {
    return DOMAIN.substring(0, DOMAIN.lastIndex) + this
}