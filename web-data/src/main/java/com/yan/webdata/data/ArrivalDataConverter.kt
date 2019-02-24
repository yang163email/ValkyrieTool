package com.yan.webdata.data

import android.util.Log
import com.yan.webdata.bean.MainArrivalBean
import com.yan.webdata.bean.MainPartBean
import com.yan.webdata.common.TITLE_BAR
import com.yan.webdata.common.TITLE_BAR_IMG
import com.yan.webdata.common.TITLE_BAR_TEXT
import com.yan.webdata.service.RxArrivalService
import com.yan.webdata.utils.appendDomain
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jsoup.Jsoup
import org.jsoup.nodes.Element

/**
 *  @author      : yan
 *  @date        : 2018/7/2 14:33
 *  @description : TODO
 */
object ArrivalDataConverter {

    private val TAG = javaClass.simpleName

    private val repository: ArrivalRepository

    init {
        repository = ArrivalRepository()
    }

    /**
     * 将首页数据，转成数据bean
     */
    fun getMainData(saved: Boolean = false): ArrayList<MainArrivalBean> {
        try {
            val mainOriginal = repository.getMainOriginal(saved)
            return parseMainData(mainOriginal)
        } catch (e: Exception) {
            throw e
        }
    }

    fun getRxMainData(observer: Observer<ArrayList<MainArrivalBean>>) {
        RxArrivalService.getMain()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map {
                    parseMainData(it)
                }.subscribe(observer)
    }

    /**
     * 获取详情数据
     * 暂时没有使用
     */
    fun getDetailData(url: String, saved: Boolean = true) {
        repository.getDetailOriginal(url, saved)
    }

    private fun parseMainData(mainOriginal: String): ArrayList<MainArrivalBean> {
        val mainArrivalBeanList = arrayListOf<MainArrivalBean>()
        if (mainOriginal.isEmpty()) {
            Log.e(TAG, "parseMainData(): ${javaClass.name}#getMainData() 未获取到数据")
        }

        val doc = Jsoup.parse(mainOriginal)
        //先找到titlebar
        val titlebarElements = doc.getElementsByClass(TITLE_BAR)

        var parent: Element? = null
        var partList = arrayListOf<ArrayList<MainPartBean>>()
        titlebarElements.forEachIndexed { index, element ->
            //图片url
            val titleBarImgs = element.getElementsByClass(TITLE_BAR_IMG)
            val titleBarImg = titleBarImgs[0]
            val imgSrc = titleBarImg.attr("src").appendDomain()

            //title文字
            val titleBarTexts = element.getElementsByClass(TITLE_BAR_TEXT)
            val titleBarText = titleBarTexts[0]
            val text = titleBarText.text()

            //中间元素,只需要执行一次即可
            if (parent == null) {
                //拿到上一级标签
                parent = element.parent()
                partList = getPartList(parent)
            }
            val mainArrivalBean = MainArrivalBean(imgSrc, partList[index], text)
            mainArrivalBeanList.add(mainArrivalBean)
        }
        return mainArrivalBeanList
    }

    private fun getPartList(element: Element?): ArrayList<ArrayList<MainPartBean>> {
        val partBeanList = arrayListOf<ArrayList<MainPartBean>>()
        if (element == null) return partBeanList
        //<table class="main">
        val mainElements = element.getElementsByClass("main")
        mainElements.forEach {
            val subPartBeanList = arrayListOf<MainPartBean>()
            //a标签
            val select = it.select("a")
            select.forEach {
                val href = it.attr("href").appendDomain()
                val imgUrl = it.select("img").attr("data-src").appendDomain()
                val name = it.select("p").text()
                val mainPartBean = MainPartBean(href, imgUrl, name)
                subPartBeanList.add(mainPartBean)
            }
            partBeanList.add(subPartBeanList)
        }
        return partBeanList
    }
}