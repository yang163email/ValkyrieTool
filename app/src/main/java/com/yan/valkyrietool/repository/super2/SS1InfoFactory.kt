package com.yan.valkyrietool.repository.super2

import com.yan.valkyrietool.bean.ArrivalBoss
import com.yan.valkyrietool.bean.Character
import com.yan.valkyrietool.bean.OtherEnemy
import com.yan.valkyrietool.bean.Team
import com.yan.valkyrietool.data.ArrivalId
import com.yan.valkyrietool.data.SHADOW_CRYSTAL_AVATAR_MAGIC
import com.yan.valkyrietool.data.SHADOW_CRYSTAL_AVATAR_PHYSICS
import com.yan.valkyrietool.data.STAR_S_IMG_URL
import com.yan.valkyrietool.repository.BaseArrivalBossInfoFactory

/**
 *  @author : yan
 *  @date   : 2018/6/27 14:39
 *  @desc   : 毁灭龙神诸神黄昏 boss信息生成工厂
 */
class SS1InfoFactory : BaseArrivalBossInfoFactory() {

    override fun generateOtherEnemies(): ArrayList<OtherEnemy> {
        val otherEnemies = arrayListOf<OtherEnemy>()
        val middleEnemy = OtherEnemy("力之水晶[暗]", "力之水晶[暗]", SHADOW_CRYSTAL_AVATAR_PHYSICS, MIDDLE,
                "对组队战斗的参加全员施展黑暗×5回合。提升自己物理防御×2回合&为诸神黄昏施展物理反射盾×2回合。\n" +
                        "※黑暗对人类丶精灵丶巨人有效\n" +
                        "※物理反射护盾仅在诸神黄昏HP减少到第1、3层时发动",
                CRYSTAL_MIDDLE, CRYSTAL_MIDDLE, CRYSTAL_MIDDLE, CRYSTAL_LOW, CRYSTAL_HIGH)

        val behindEnemy = OtherEnemy("魔之水晶[暗]", "魔之水晶[暗]", SHADOW_CRYSTAL_AVATAR_MAGIC, BEHIND,
                "对组队战斗的参加全员施展强力混沌力场&给予最近的敌人1名强力的暗属性魔法攻击。并提升自己的魔法防御×2回合&为诸神黄昏施展魔法反射护盾×2回合。\n" +
                        "※魔法反射护盾仅在诸神黄昏HP减少到第2、3层时发动",
                CRYSTAL_MIDDLE, CRYSTAL_MIDDLE, CRYSTAL_MIDDLE, CRYSTAL_LOW, CRYSTAL_HIGH)
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
        val bossEnemy = ArrivalBoss(ArrivalId.SS1, "诸神黄昏", "毁灭龙神诸神黄昏",
                "http://jam-capture-vcon-ww.ateamid.com/images/character/icon_character_207005_1.png",
                SUPER2, STAR_S3, STAR_S_IMG_URL, MIDDLE,
                "[ 悲惨终幕 ]",
                "给予敌方全体威力40%×4次暗属性物理攻击 & 暗伤丶脱力×3回合　\n※魔法攻击变成物理攻击　\n※暗伤对地面上敌人有效　\n※脱力对人类丶精灵丶巨人有效",
                "给予组队战斗参加全员护盾解除&暗属性魔法攻击&暗伤丶脱力×3回合&蚀之力场&重力场\n" +
                        "※护盾解除对全种族有效\n" +
                        "※暗伤对地上的敌人有效\n" +
                        "※脱力对人类丶精灵丶巨人有效\n" +
                        "※重力场仅会在诸神黄昏HP减少到第2丶3层时发动",
                2.5f, 2f, 2f, 0.5f, 3.4f,
                otherEnemies, team)
        return bossEnemy
    }
}