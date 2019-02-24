package com.yan.webdata

import org.junit.Test

/**
 *  @author : yan
 *  @date   : 2018/7/11 10:59
 *  @desc   : todo
 */
class KtTest {

    @Test
    fun test() {
        val url = "http://jam-capture-vcon-ww.ateamid.com/zh_CN/character_list/1.html?filter=#1"
        val result = url.substringBeforeLast('/').substringBeforeLast('/')
        println(result)
    }
}