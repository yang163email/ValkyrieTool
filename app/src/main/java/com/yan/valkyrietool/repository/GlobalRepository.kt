package com.yan.valkyrietool.repository

import com.yan.valkyrietool.bean.ArrivalBoss
import com.yan.valkyrietool.bean.TotalArrivalBean
import com.yan.valkyrietool.bean.TotalPartBean

/**
 *  @author : yan
 *  @date   : 2018/6/26 17:01
 *  @desc   : 全局数据仓库
 */
object GlobalRepository {

    private val mainHeaderUrl = "http://jam-capture-vcon-ww.ateamid.com/images/titlebar.png"

    //超降临
    private val superImages = arrayOf(
            "http://jam-capture-vcon-ww.ateamid.com/images/character/icon_character_307003_1.png",
            "http://jam-capture-vcon-ww.ateamid.com/images/character/icon_character_206001_1.png",
            "http://jam-capture-vcon-ww.ateamid.com/images/character/icon_character_206003_1.png",
            "http://jam-capture-vcon-ww.ateamid.com/images/character/icon_character_206006_1.png",
            "http://jam-capture-vcon-ww.ateamid.com/images/character/icon_character_106007_1.png"
    )
    private val superDescs = arrayOf(
            "奥弗尼尔", "尤弥尔", "安格尔波达",
            "米米尔超降临", "贝斯特拉")

    //降临
    private val normalImages = arrayOf(
            "http://jam-capture-vcon-ww.ateamid.com/images/character/icon_character_107001_1.png",
            "http://jam-capture-vcon-ww.ateamid.com/images/character/icon_character_106004_1.png",
            "http://jam-capture-vcon-ww.ateamid.com/images/character/icon_character_207003_1.png",
            "http://jam-capture-vcon-ww.ateamid.com/images/character/icon_character_106005_1.png",
            "http://jam-capture-vcon-ww.ateamid.com/images/character/icon_character_307002_1.png",
            "http://jam-capture-vcon-ww.ateamid.com/images/character/icon_character_107005_1.png",
            "http://jam-capture-vcon-ww.ateamid.com/images/character/icon_character_306001_1.png",
            "http://jam-capture-vcon-ww.ateamid.com/images/character/icon_character_105011_1.png",
            "http://jam-capture-vcon-ww.ateamid.com/images/character/icon_character_302020_1.png",
            "http://jam-capture-vcon-ww.ateamid.com/images/character/icon_character_104003_1.png",
            "http://jam-capture-vcon-ww.ateamid.com/images/character/icon_character_207004_1.png",
            "http://jam-capture-vcon-ww.ateamid.com/images/character/icon_character_207001_1.png",
            "http://jam-capture-vcon-ww.ateamid.com/images/character/icon_character_306001_1.png",
            "http://jam-capture-vcon-ww.ateamid.com/images/character/icon_character_206002_1.png",
            "http://jam-capture-vcon-ww.ateamid.com/images/character/icon_character_107006_1.png",
            "http://jam-capture-vcon-ww.ateamid.com/images/character/icon_character_301011_1.png",
            "http://jam-capture-vcon-ww.ateamid.com/images/character/icon_character_206005_1.png",
            "http://jam-capture-vcon-ww.ateamid.com/images/character/icon_character_107905_1.png",
            "http://jam-capture-vcon-ww.ateamid.com/images/character/icon_character_106002_1.png",
            "http://jam-capture-vcon-ww.ateamid.com/images/character/icon_character_107003_1.png",
            "http://jam-capture-vcon-ww.ateamid.com/images/character/icon_character_106001_1.png",
            "http://jam-capture-vcon-ww.ateamid.com/images/character/icon_character_307001_1.png",
            "http://jam-capture-vcon-ww.ateamid.com/images/character/icon_character_106006_1.png",
            "http://jam-capture-vcon-ww.ateamid.com/images/character/icon_character_105014_1.png",
            "http://jam-capture-vcon-ww.ateamid.com/images/character/icon_character_107906_1.png",
            "http://jam-capture-vcon-ww.ateamid.com/images/character/icon_character_105016_1.png",
            "http://jam-capture-vcon-ww.ateamid.com/images/character/icon_character_207006_1.png",
            "http://jam-capture-vcon-ww.ateamid.com/images/character/icon_character_207903_2.png",
            "http://jam-capture-vcon-ww.ateamid.com/images/character/icon_character_205008_1.png",
            "http://jam-capture-vcon-ww.ateamid.com/images/character/icon_character_106010_1.png",
            "http://jam-capture-vcon-ww.ateamid.com/images/character/icon_character_201001_1.png"
    )
    private val normalDescs = arrayOf(
            "芬里尔", "埃吉尔", "尼德霍格",
            "孛鲁梭降临", "赫拉斯瓦尔格", "圣兽斯雷普尼尔",
            "万圣节梅尔玛乐", "卢布", "豪鬼降临",
            "韦兰", "niconico televi-chan", "耶梦加得",
            "梅尔玛乐", "提亚奇", "海德伦",
            "梅杰德超", "圣诞提亚奇", "福临加姆",
            "赫朗格尼尔", "法芙尼尔", "史尔特尔",
            "维德佛尔尼尔", "辛莫拉", "八手",
            "年兽海德伦", "祥彤", "第13号机疑似神化<显现>",
            "集结之时", "水之祝福", "巴利托斯",
            "奥丁")

    private val mTotalDatas = arrayListOf<TotalArrivalBean>()

    private var super2ArrivalRepository: Super2ArrivalRepository

    init {
        super2ArrivalRepository = Super2ArrivalRepository()
    }

    /**
     * 获取主页全局数据
     */
    /*fun getMainTotalData(): ArrayList<TotalArrivalBean> {
        if (mTotalDatas.isNotEmpty()) return mTotalDatas
        //超绝
        val super2partList = arrayListOf<TotalPartBean>()
        for (i in 0 until super2Images.size) {
            val super2Part = TotalPartBean("", super2Images[i], super2Descs[i])
            super2partList.add(super2Part)
        }
        val super2Bean = TotalArrivalBean(super2partList, mainHeaderUrl, SUPER2)
        //超
        val superPartList = arrayListOf<TotalPartBean>()
        for (i in 0 until superImages.size) {
            val superPart = TotalPartBean("", superImages[i], superDescs[i])
            superPartList.add(superPart)
        }
        val superBean = TotalArrivalBean(superPartList, mainHeaderUrl, SUPER)
        //普通
        val normalPartList = arrayListOf<TotalPartBean>()
        for (i in 0 until normalImages.size) {
            val normalPart = TotalPartBean("", normalImages[i], normalDescs[i])
            normalPartList.add(normalPart)
        }
        val normalBean = TotalArrivalBean(normalPartList, mainHeaderUrl, NORMAL)

        mTotalDatas.apply {
            add(super2Bean)
            add(superBean)
            add(normalBean)
        }
        return mTotalDatas
    }*/

    /**
     * 获取主页全局数据
     */
    fun getMainTotalData2(): ArrayList<TotalArrivalBean> {
        if (mTotalDatas.isNotEmpty()) return mTotalDatas
        val bossList = super2ArrivalRepository.bossList
        val super2partList = arrayListOf<TotalPartBean>()
        var level: Int = -1
        bossList.forEach {
            if (level == -1)
                level = it.level
            val super2Part = TotalPartBean(it.id, it.avatar, it.simpleName)
            super2partList.add(super2Part)
        }
        val super2Bean = TotalArrivalBean(super2partList, level)
        mTotalDatas.add(super2Bean)

        return mTotalDatas
    }

    /**
     * 查询详情数据
     */
    fun getDetailInfo(id: String): ArrivalBoss {
        val bossList = super2ArrivalRepository.bossList
        val filterList = bossList.filter { id == it.id }
        if (filterList.isEmpty()) {
            throw IllegalStateException("invalid id")
        }
        if (filterList.size > 1) {
            throw IllegalStateException("id not unique")
        }
        return filterList.first()
    }
}