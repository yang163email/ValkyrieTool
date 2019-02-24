package com.yan.webdata.data

import android.util.Log
import com.yan.webdata.bean.Character
import com.yan.webdata.common.CharacterConst.ATTR_AVATAR
import com.yan.webdata.common.CharacterConst.CLASS_FILTER
import com.yan.webdata.common.CharacterConst.CLASS_NAME
import com.yan.webdata.common.CharacterConst.SITE_BEHIND
import com.yan.webdata.common.CharacterConst.SITE_FRONT
import com.yan.webdata.common.CharacterConst.SITE_MIDDLE
import com.yan.webdata.common.CharacterConst.VALUE_AS_ATTR
import com.yan.webdata.common.CharacterConst.VALUE_ATTR_RESISTANCE
import com.yan.webdata.common.CharacterConst.VALUE_AWAKENING_AS_ATTR
import com.yan.webdata.common.CharacterConst.VALUE_GENDER
import com.yan.webdata.common.CharacterConst.VALUE_INIT_STAR
import com.yan.webdata.common.CharacterConst.VALUE_NEW_SORT
import com.yan.webdata.common.CharacterConst.VALUE_RACE
import com.yan.webdata.common.CharacterConst.VALUE_SITE_SORT
import com.yan.webdata.common.DOMAIN
import com.yan.webdata.service.RxCharacterService
import com.yan.webdata.utils.appendDomain
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Function3
import io.reactivex.schedulers.Schedulers
import org.jsoup.Jsoup
import org.jsoup.select.Elements

/**
 *  @author      : yan
 *  @date        : 2018/7/2 14:33
 *  @description : 角色数据转换器
 */
object CharacterDataConverter {

    private val TAG = javaClass.simpleName

    private val repository: CharacterRepository

    init {
        repository = CharacterRepository()
    }

    /**
     * 将首页数据，转成数据bean
     */
    fun getCharacters(site: Int, saved: Boolean = false): ArrayList<Character> {
        val original = repository.getCharacters(site, saved)
        return parseCharacters(original, site)
    }

    fun getRxCharacters(site: Int, observer: Observer<ArrayList<Character>>) {
        RxCharacterService.getCharacters(site)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map {
                    val pair = Pair("", it)
                    parseCharacters(pair, site)
                }
                .subscribe(observer)
    }

    fun getRxAllCharacters(observer: Observer<ArrayList<Character>>) {
        val dataList = arrayListOf<Character>()
        val observable1 = RxCharacterService.getCharacters(SITE_FRONT)
        val observable2 = RxCharacterService.getCharacters(SITE_MIDDLE)
        val observable3 = RxCharacterService.getCharacters(SITE_BEHIND)
        Observable.zip(observable1, observable2, observable3,
                Function3<String, String, String, ArrayList<Character>> {
                    t1, t2, t3 ->
                    val characters1 = parseCharacters(Pair("", t1), SITE_FRONT)
                    val characters2 = parseCharacters(Pair("", t2), SITE_MIDDLE)
                    val characters3 = parseCharacters(Pair("", t3), SITE_BEHIND)
                    dataList.addAll(characters1)
                    dataList.addAll(characters2)
                    dataList.addAll(characters3)
                    dataList
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer)

    }

    private fun parseCharacters(original: Pair<String, String>, site: Int): ArrayList<Character> {
        val characterList = arrayListOf<Character>()
        if (original.second.isEmpty()) {
            Log.e(TAG, "parseCharacters(): ${javaClass.name}#getCharacters() 未获取到数据")
        }

        val doc = Jsoup.parse(original.second)
        //先找到class=filter
        //<td class="filter">
        val filterElements = doc.getElementsByClass(CLASS_FILTER)

        filterElements.forEach {
            //<input type="hidden" name="vcon_f_ww_new_sort" value="0"/>
            val newSortElements = it.getElementsByAttributeValue("name", VALUE_NEW_SORT)
            checkElementsSize(newSortElements, VALUE_NEW_SORT)
            //最新排序
            val newSort = newSortElements[0].attr("value").toInt()

            //<input type="hidden" name="vcon_f_ww_pt_sort" value="89"/>
            val siteSortElements = it.getElementsByAttributeValue("name", VALUE_SITE_SORT)
            checkElementsSize(siteSortElements, VALUE_SITE_SORT)
            //位置排序
            val siteSort = siteSortElements[0].attr("value").toInt()

            //<input type="hidden" name="vcon_f_ww_race" value="2"/>
            val raceElements = it.getElementsByAttributeValue("name", VALUE_RACE)
            checkElementsSize(raceElements, VALUE_RACE)
            //种族
            val race = raceElements[0].attr("value")

            //<input type="hidden" name="vcon_f_ww_init_star" value="3"/>
            val initStarElements = it.getElementsByAttributeValue("name", VALUE_INIT_STAR)
            checkElementsSize(initStarElements, VALUE_INIT_STAR)
            //初始星级
            val initStar = initStarElements[0].attr("value")

            //<input type="hidden" name="vcon_f_ww_gender" value="2"/>
            val genderElements = it.getElementsByAttributeValue("name", VALUE_GENDER)
            checkElementsSize(genderElements, VALUE_GENDER)
            //性别
            val gender = genderElements[0].attr("value")

            //<input type="hidden" name="vcon_f_ww_revision_attr" value="revision_attr_fire,revision_attr_water," seach="or"/>
            val attrResistanceElements = it.getElementsByAttributeValue("name", VALUE_ATTR_RESISTANCE)
            checkElementsSize(attrResistanceElements, VALUE_ATTR_RESISTANCE)
            //属性抗性
            val attrResistance = attrResistanceElements[0].attr("value")

            //<input type="hidden" name="vcon_f_ww_action_skill_element" value="2," seach="and"/>
            val aSAttrElements = it.getElementsByAttributeValue("name", VALUE_AS_ATTR)
            checkElementsSize(aSAttrElements, VALUE_AS_ATTR)
            //主动技能属性
            val aSAttr = aSAttrElements[0].attr("value")

            //<input type="hidden" name="vcon_f_ww_awakening_action_skill_element" value="," seach="and"/>
            val awakeningASAttrElements = it.getElementsByAttributeValue("name", VALUE_AWAKENING_AS_ATTR)
            checkElementsSize(awakeningASAttrElements, VALUE_AWAKENING_AS_ATTR)
            //主动技能属性(觉醒)
            val awakeningASAttr = awakeningASAttrElements[0].attr("value")

            val linkElements = it.getElementsByTag("a")
            checkElementsSize(linkElements, "linkUrl")
            //跳转链接
            var linkUrl = linkElements[0].attr("href")
            if (linkUrl.startsWith("../")) {
                val preUrl: String
                val url = original.first
                if (url.isEmpty()) {
                    preUrl = DOMAIN + "zh_CN"
                } else {
                    preUrl = url.substringBeforeLast('/').substringBeforeLast('/')
                }
                linkUrl = preUrl + linkUrl.substringAfter("..")

            }

            val avatarElements = linkElements[0].getElementsByAttribute(ATTR_AVATAR)
            checkElementsSize(avatarElements, ATTR_AVATAR)
            //头像url
            val avatar = avatarElements[0].attr(ATTR_AVATAR).appendDomain()
            val nameElements = linkElements[0].getElementsByClass(CLASS_NAME)
            checkElementsSize(nameElements, CLASS_NAME)
            //名字
            val name = nameElements[0].text()

            val character = Character(linkUrl, name, avatar, site.toString(), newSort, siteSort, race, initStar, gender, attrResistance, aSAttr, awakeningASAttr)
            characterList.add(character)
        }
        return characterList
    }

    private fun checkElementsSize(elements: Elements, eMsg: String) {
        if (elements.size != 1) {
            throw IllegalStateException("0个或多个[$eMsg]属性")
        }
    }
}