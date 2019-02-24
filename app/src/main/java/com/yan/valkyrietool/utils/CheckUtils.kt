package com.yan.valkyrietool.utils

/**
 *  @author : yan
 *  @date   : 2018/6/28 12:27
 *  @desc   : 检测数据工具
 */
object CheckUtils {

    /**
     * 检测数据
     */
    fun checkData(standard: Float, eMsg: String, vararg values: Float) {
        val result = values.any { it > standard }
        if (result) throw IllegalStateException(eMsg)
    }
}