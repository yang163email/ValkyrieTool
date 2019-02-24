package com.yan.valkyrietool.repository

import com.yan.valkyrietool.bean.ArrivalBoss
import com.yan.valkyrietool.bean.OtherEnemy
import com.yan.valkyrietool.bean.Team

/**
 *  @author : yan
 *  @date   : 2018/6/27 14:40
 *  @desc   : 降临boss信息工厂基类
 */
abstract class BaseArrivalBossInfoFactory {

    companion object {
        /**
         * 降临级别
         */
        const val SUPER2 = 1
        const val SUPER = 2
        const val NORMAL = 3

        /**
         * 星级
         */
        const val STAR1 = 1
        const val STAR2 = 2
        const val STAR3 = 3
        const val STAR4 = 4
        const val STAR5 = 5
        const val STAR6 = 6
        const val STAR7 = 7
        const val STAR8 = 8
        const val STAR_S1 = -1
        const val STAR_S2 = -2
        const val STAR_S3 = -3

        /**
         * 站位
         */
        const val FRONT = 1
        const val MIDDLE = 2
        const val BEHIND = 3

        //水晶抗性
        const val CRYSTAL_LOW = 0f
        const val CRYSTAL_MIDDLE = 3.5f
        const val CRYSTAL_HIGH = 4f
    }

    /**
     * 生成水晶
     */
    protected abstract fun generateOtherEnemies(): ArrayList<OtherEnemy>

    /**
     * 生成推荐队伍
     * TODO 暂时未做
     */
    protected abstract fun generateRecommendTeam(): Team

    /**
     * 生成boss信息
     */
    protected abstract fun generateBossEnemy(otherEnemies: ArrayList<OtherEnemy>, team: Team): ArrivalBoss

    /**
     * 外部调用获取
     */
    fun getBossEnemyInfo(): ArrivalBoss {
        val otherEnemies = generateOtherEnemies()
        val team = generateRecommendTeam()
        return generateBossEnemy(otherEnemies, team)
    }
}