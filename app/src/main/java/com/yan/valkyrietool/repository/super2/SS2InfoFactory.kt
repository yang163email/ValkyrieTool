package com.yan.valkyrietool.repository.super2

import com.yan.valkyrietool.bean.ArrivalBoss
import com.yan.valkyrietool.bean.Character
import com.yan.valkyrietool.bean.OtherEnemy
import com.yan.valkyrietool.bean.Team
import com.yan.valkyrietool.data.ArrivalId
import com.yan.valkyrietool.data.STAR_S_IMG_URL
import com.yan.valkyrietool.data.WOOD_CRYSTAL_AVATAR_MAGIC
import com.yan.valkyrietool.data.WOOD_CRYSTAL_AVATAR_PHYSICS
import com.yan.valkyrietool.repository.BaseArrivalBossInfoFactory

/**
 *  @author : yan
 *  @date   : 2018/6/27 14:39
 *  @desc   : 终焉魔女沃普尔吉斯 boss信息生成工厂
 */
class SS2InfoFactory : BaseArrivalBossInfoFactory() {

    override fun generateOtherEnemies(): ArrayList<OtherEnemy> {
        val otherEnemies = arrayListOf<OtherEnemy>()
        val middleEnemy = OtherEnemy("魔之水晶[木]", "魔之水晶[木]", WOOD_CRYSTAL_AVATAR_MAGIC, MIDDLE,
                "给予组队战斗全员树伤×2回合。为自己施展魔法护盾&掩护沃普尔吉斯×3回合。\n" +
                        "※树伤对全种族有效\n" +
                        "※掩护仅会在沃普尔吉斯的HP在第1、3层发动\n",
                CRYSTAL_LOW, CRYSTAL_MIDDLE, CRYSTAL_HIGH, CRYSTAL_MIDDLE, CRYSTAL_MIDDLE)

        val behindEnemy = OtherEnemy("力之水晶[木]", "力之水晶[木]", WOOD_CRYSTAL_AVATAR_PHYSICS, BEHIND,
                "给予组队战斗全员强力圣光力场&给予最近的敌人1名强力木属性魔法攻击&降低技能量表。为自己施展物理护盾&回复沃普尔吉斯的HP。\n" +
                        "※HP回复仅会在沃普尔吉斯的HP减少到第2、3层时发动",
                CRYSTAL_LOW, CRYSTAL_MIDDLE, CRYSTAL_HIGH, CRYSTAL_MIDDLE, CRYSTAL_MIDDLE)
        otherEnemies.add(middleEnemy)
        otherEnemies.add(behindEnemy)
        return otherEnemies
    }

    override fun generateRecommendTeam(): Team {
        //推荐队伍
        val characters = arrayListOf<Character>()

        val character1 = Character("shortName1", "fullName1", "", FRONT)
        val character2 = Character("shortName2", "fullName2", "", FRONT)
        val character3 = Character("shortName3", "fullName3", "", FRONT)
        val character4 = Character("shortName4", "fullName4", "", FRONT)
        val character5 = Character("shortName5", "fullName5", "", FRONT)
        characters.apply {
            add(character1)
            add(character2)
            add(character3)
            add(character4)
            add(character5)
        }

        val team = Team(characters)
        return team
    }

    override fun generateBossEnemy(otherEnemies: ArrayList<OtherEnemy>, team: Team): ArrivalBoss {
        val bossEnemy = ArrivalBoss(ArrivalId.SS2, "沃普尔吉斯", "终焉魔女沃普尔吉斯",
                "http://jam-capture-vcon-ww.ateamid.com/images/character/icon_character_201020_1.png",
                SUPER2, STAR_S3, STAR_S_IMG_URL, MIDDLE,
                "终结时刻",
                "给予敌人全体威力20%×8次木属性魔法攻击&混乱×3回合\n" +
                        "※护盾无视\n" +
                        "※混乱对神族、精灵、巨人有效",
                "给予组队战斗全员木属性魔法攻击&混乱×3回合&重力场&为自己施展护盾×3回合\n" +
                        "※混乱对神族、精灵、巨人有效\n" +
                        "※护盾仅会在沃普尔吉斯HP减少到第2、3层时发动\n" +
                        "※护盾在受到15次攻击时消失",
                1f, 2f, 2.5f, 1.6f, 3f,
                otherEnemies, team)
        return bossEnemy
    }
}