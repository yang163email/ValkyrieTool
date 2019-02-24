package com.yan.valkyrietool.repository

import com.yan.valkyrietool.repository.super2.SS1InfoFactory
import com.yan.valkyrietool.repository.super2.SS2InfoFactory

/**
 *  @author : yan
 *  @date   : 2018/6/27 11:41
 *  @desc   : 超绝降临数据仓库
 */
class Super2ArrivalRepository : BaseRepository() {

    private val ss1InfoFactory: SS1InfoFactory = SS1InfoFactory()
    private val ss2InfoFactory: SS2InfoFactory = SS2InfoFactory()

    init {
        createData()
    }

    /**
     * 创建超绝降临数据
     */
    override fun createData() {
        synchronized(bossList) {
            if (bossList.isNotEmpty()) return
            val ss1BossEnemy = ss1InfoFactory.getBossEnemyInfo()
            val ss2BossEnemy = ss2InfoFactory.getBossEnemyInfo()

            bossList.add(ss1BossEnemy)
            bossList.add(ss2BossEnemy)
        }
    }

}