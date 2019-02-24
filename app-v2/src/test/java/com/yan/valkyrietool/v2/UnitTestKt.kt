package com.yan.valkyrietool.v2

import org.junit.Test

/**
 *  @author : yan
 *  @date   : 2018/7/5 10:59
 *  @desc   : todo
 */
class UnitTestKt {

    @Test
    fun testList() {

        val list = arrayListOf(1, 2, 5, 3, 4, 2)
        val tempList = list.clone() as ArrayList<Int>

        tempList.forEach {
            if (it == 2)
            list.remove(it)
        }
        println(list.hashCode())
        println(tempList.hashCode())
        println("tempList: $tempList")
        println("list: $list")
    }
}