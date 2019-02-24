package com.yan.valkyrietool.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
import android.widget.LinearLayout
import com.yan.valkyrietool.R
import com.yan.valkyrietool.bean.ArrivalBoss
import com.yan.valkyrietool.data.*
import com.yan.valkyrietool.repository.BaseArrivalBossInfoFactory.Companion.BEHIND
import com.yan.valkyrietool.repository.BaseArrivalBossInfoFactory.Companion.FRONT
import com.yan.valkyrietool.repository.BaseArrivalBossInfoFactory.Companion.MIDDLE
import com.yan.valkyrietool.repository.BaseArrivalBossInfoFactory.Companion.STAR1
import com.yan.valkyrietool.repository.BaseArrivalBossInfoFactory.Companion.STAR2
import com.yan.valkyrietool.repository.BaseArrivalBossInfoFactory.Companion.STAR3
import com.yan.valkyrietool.repository.BaseArrivalBossInfoFactory.Companion.STAR4
import com.yan.valkyrietool.repository.BaseArrivalBossInfoFactory.Companion.STAR5
import com.yan.valkyrietool.repository.BaseArrivalBossInfoFactory.Companion.STAR6
import com.yan.valkyrietool.repository.BaseArrivalBossInfoFactory.Companion.STAR7
import com.yan.valkyrietool.repository.BaseArrivalBossInfoFactory.Companion.STAR8
import com.yan.valkyrietool.repository.BaseArrivalBossInfoFactory.Companion.STAR_S1
import com.yan.valkyrietool.repository.BaseArrivalBossInfoFactory.Companion.STAR_S2
import com.yan.valkyrietool.repository.BaseArrivalBossInfoFactory.Companion.STAR_S3
import com.yan.valkyrietool.repository.GlobalRepository
import com.yan.valkyrietool.utils.glide
import kotlinx.android.synthetic.main.activity_arrival_detail.*
import org.jetbrains.anko.dip
import org.jetbrains.anko.doAsync
import kotlin.concurrent.thread

/**
 *  @author : yan
 *  @date   : 2018/6/27 16:03
 *  @desc   : 降临详情界面
 */
class ArrivalDetailActivity : AppCompatActivity() {

    protected val TAG = javaClass.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_arrival_detail)
        getData()
    }

    private fun getData() {
        doAsync {
            val id = intent.getStringExtra("id")
            val detailInfo = GlobalRepository.getDetailInfo(id)
            runOnUiThread {
                showData(detailInfo)
            }
        }
    }

    private fun showData(bean: ArrivalBoss) {
//        toolbar.title = bean.simpleName
        iv_avatar.glide(bean.avatar)

        //星星level
        createIvStar(bean.star)
        //全名
        tv_full_name.text = bean.fullName

        //站位
        val siteUrl = getSiteImgUrl(bean.site)
        iv_site.glide(siteUrl)

        //主动技能
        tv_active_skill.text = "${bean.activeSkillName}\n${bean.activeSkillDesc}"
        //自动技能
        tv_auto_skill.text = bean.autoSkillDesc

        iv_other_header1.glide(HEADER_TITLE_URL)
        iv_other_header2.glide(HEADER_TITLE_URL)
        //小弟
        val enemies = bean.otherEnemies
        if (enemies.size == 2) {
            //只支持2个小弟
            val otherEnemy1 = enemies[0]
            val otherEnemy2 = enemies[1]

            iv_other_avatar1.glide(otherEnemy1.avatar)
            tv_other_short_name1.text = otherEnemy1.shortName
            other_resistance_view1.setData(otherEnemy1.resistance)
            iv_other_avatar11.glide(otherEnemy1.avatar)
            tv_other_full_name1.text = otherEnemy1.fullName
            iv_other_site1.glide(getSiteImgUrl(otherEnemy1.site))
            tv_other_desc1.text = otherEnemy1.autoSkillDesc

            iv_other_avatar2.glide(otherEnemy2.avatar)
            tv_other_short_name2.text = otherEnemy2.shortName
            other_resistance_view2.setData(otherEnemy2.resistance)
            iv_other_avatar21.glide(otherEnemy2.avatar)
            tv_other_full_name2.text = otherEnemy2.fullName
            iv_other_site2.glide(getSiteImgUrl(otherEnemy2.site))
            tv_other_desc2.text = otherEnemy2.autoSkillDesc
        }

        iv_property_header3.glide(HEADER_TITLE_URL)
        resistance_view.setData(bean.resistance)
    }

    private fun getSiteImgUrl(site: Int): String {
        return when (site) {
            FRONT -> FRONT_IMG_URL
            MIDDLE -> MIDDLE_IMG_URL
            BEHIND -> BEHIND_IMG_URL
            else -> FRONT_IMG_URL
        }
    }

    private fun createIvStar(star: Int) {
        when (star) {
            STAR_S1 -> inflateIvStar(STAR_S_IMG_URL)
            STAR_S2 -> repeat(2) { inflateIvStar(STAR_S_IMG_URL) }
            STAR_S3 -> repeat(3) { inflateIvStar(STAR_S_IMG_URL) }
            STAR1 -> inflateIvStar(STAR_IMG_URL)
            STAR2 -> repeat(2) { inflateIvStar(STAR_IMG_URL) }
            STAR3 -> repeat(3) { inflateIvStar(STAR_IMG_URL) }
            STAR4 -> repeat(4) { inflateIvStar(STAR_IMG_URL) }
            STAR5 -> repeat(5) { inflateIvStar(STAR_IMG_URL) }
            STAR6 -> repeat(6) { inflateIvStar(STAR_IMG_URL) }
            STAR7 -> repeat(7) { inflateIvStar(STAR_IMG_URL) }
            STAR8 -> repeat(8) { inflateIvStar(STAR_IMG_URL) }
        }
    }

    private fun inflateIvStar(starUrl: String) {
        val ivStar = ImageView(this)
        val lp = LinearLayout.LayoutParams(dip(30), dip(30))
        ivStar.layoutParams = lp
        ivStar.glide(starUrl)
        ll_star_container.addView(ivStar)
    }
}