package com.yan.valkyrietool.repository

import com.yan.valkyrietool.bean.ArrivalBoss

/**
 *  @author : yan
 *  @date   : 2018/6/27 11:51
 *  @desc   : 基类仓库
 */
abstract class BaseRepository {

    companion object {

    }

    val bossList = arrayListOf<ArrivalBoss>()

    protected abstract fun createData()
}