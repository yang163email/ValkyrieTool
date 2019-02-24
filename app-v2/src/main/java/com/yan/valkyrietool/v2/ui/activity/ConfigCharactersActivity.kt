package com.yan.valkyrietool.v2.ui.activity

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.SearchView
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.TextView
import com.yan.valkyrietool.v2.R
import com.yan.valkyrietool.v2.base.BaseActivity
import com.yan.valkyrietool.v2.db.PersonalCharactersDBManager
import com.yan.valkyrietool.v2.event.AddCharactersEvent
import com.yan.valkyrietool.v2.ui.adapter.AddTeamAdapter
import com.yan.valkyrietool.v2.utils.Pinyin4j
import com.yan.valkyrietool.v2.utils.startLoading
import com.yan.valkyrietool.v2.utils.stopLoading
import com.yan.valkyrietool.v2.widget.rv.SpaceItemDecoration
import com.yan.webdata.bean.Character
import com.yan.webdata.common.CharacterConst.SITE_BEHIND
import com.yan.webdata.common.CharacterConst.SITE_FRONT
import com.yan.webdata.common.CharacterConst.SITE_MIDDLE
import com.yan.webdata.data.CharacterDataConverter
import kotlinx.android.synthetic.main.activity_config_characters.*
import kotlinx.android.synthetic.main.layout_loading.*
import kotlinx.android.synthetic.main.pop_site_all.view.*
import org.greenrobot.eventbus.EventBus
import org.jetbrains.anko.dip
import org.jetbrains.anko.find
import org.jetbrains.anko.toast

/**
 *  @author : yan
 *  @date   : 2018/7/7 14:08
 *  @desc   : 配置角色界面
 */
class ConfigCharactersActivity : BaseActivity(), Toolbar.OnMenuItemClickListener, View.OnClickListener {

    companion object {
        const val ALL = -1
        const val FRONT = SITE_FRONT
        const val MIDDLE = SITE_MIDDLE
        const val BEHIND = SITE_BEHIND
    }

    private lateinit var addTeamAdapter: AddTeamAdapter
    private var originalDataList = arrayListOf<Character>()
    private val pinyin4j by lazy { Pinyin4j() }

    private var currentSite = ALL

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_config_characters)

        initView()
        loadAllData()
    }

    private fun initView() {
        tv_title.text = "全部"
        toolbar.apply {
            inflateMenu(R.menu.activity_config_characters_menu)
            val item = menu.findItem(R.id.search)
            val searchView = item.actionView as SearchView
            initSearchView(searchView)
            setOnMenuItemClickListener(this@ConfigCharactersActivity)
        }

        addTeamAdapter = AddTeamAdapter(::itemClick) {}
        recycler_view.apply {
            adapter = addTeamAdapter
            layoutManager = GridLayoutManager(this@ConfigCharactersActivity, 4)
            addItemDecoration(SpaceItemDecoration(leftSpace = dip(2), rightSpace = dip(2), bottomSpace = dip(2)))
        }
        tv_title.setOnClickListener(this)
    }

    private fun initSearchView(searchView: SearchView) {
        searchView.queryHint = "查找角色"

        val searchAutoComplete = searchView.find<SearchView.SearchAutoComplete>(R.id.search_src_text);
        //设置输入框提示文字样式
        searchAutoComplete.setHintTextColor(resources.getColor(R.color.c_cccccc));//设置提示文字颜色
        searchAutoComplete.setTextColor(resources.getColor(android.R.color.white));//设置内容文字颜色
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                //不处理提交
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                //这里处理事件
                filterCharacters(newText)
                return true
            }

        })
    }

    /**
     * 筛选角色
     */
    private fun filterCharacters(newText: String?) {
        if (newText == null || newText.isEmpty()) {
            //需要重置
            resetDataList()
        } else {
            val filterList = addTeamAdapter.dataList.filter {
                it.name.contains(newText) || getPinyin(it.name).contains(newText, true)
            } as ArrayList<Character>
            addTeamAdapter.dataList = filterList
        }
    }

    private fun getPinyin(str: String): String {
        return pinyin4j.toPinYinUppercase(str)
    }

    private fun resetDataList() {
        filterData(currentSite)
    }

    private fun loadAllData() {
        iv_header_loading.startLoading()
        CharacterDataConverter.getRxAllCharacters(object : BaseObserver<ArrayList<Character>>(this) {
            override fun onNext(t: ArrayList<Character>) {
                //查询数据库
                val list = PersonalCharactersDBManager.query(this@ConfigCharactersActivity)
                originalDataList.clear()
                originalDataList.addAll(t)
                originalDataList.removeAll(list)
                addTeamAdapter.dataList = originalDataList
            }
        })
    }

    override fun hideLoading() {
        iv_header_loading.stopLoading()
    }

    private fun itemClick(character: Character, position: Int) {
        character.isSelect = !character.isSelect
        addTeamAdapter.notifyItemChanged(position)
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        val select = originalDataList.any { it.isSelect }
        if (!select) {
            toast("没有选中的角色")
            return true
        }
        originalDataList.filter {
            it.isSelect
        }.forEach {
            //存储数据库
            PersonalCharactersDBManager.insert(this, it)
        }
        EventBus.getDefault().post(AddCharactersEvent())
        toast("添加成功")
        finish()
        return true
    }

    override fun onClick(v: View?) {
        showPopupWindow()
    }

    private var popupWindow: PopupWindow? = null
    private var popupView: View? = null

    private fun showPopupWindow() {
        if (popupWindow == null) {
            popupView = layoutInflater.inflate(R.layout.pop_site_all, null)
            popupWindow = PopupWindow(popupView, dip(200), LinearLayout.LayoutParams.WRAP_CONTENT, true)
            popupWindow?.setBackgroundDrawable(ColorDrawable(Color.WHITE))
        }
        //设置勾选项
        val text = tv_title.text
        popupView?.apply {
            when (text) {
            //第一个勾选、第二个、3个
                tv_front.text -> selectPopView(0)
                tv_middle.text -> selectPopView(1)
                tv_behind.text -> selectPopView(2)
                tv_all.text -> selectPopView(3)
            }
            tv_front.setOnClickListener {
                popItemClick(tv_front)
                currentSite = FRONT
            }
            tv_middle.setOnClickListener {
                popItemClick(tv_middle)
                currentSite = MIDDLE
            }
            tv_behind.setOnClickListener {
                popItemClick(tv_behind)
                currentSite = BEHIND
            }
            tv_all.setOnClickListener {
                popItemClick(tv_all)
                currentSite = ALL
            }

        }
        popupWindow?.showAsDropDown(tv_title)
    }

    private fun selectPopView(position: Int) {
        popupView?.apply {
            val drawable = resources.getDrawable(R.mipmap.right)
            drawable.setBounds(0, 0, drawable.minimumWidth, drawable.minimumHeight)
            when (position) {
                0 -> {
                    tv_all.setCompoundDrawables(null, null, null, null)
                    tv_front.setCompoundDrawables(null, null, drawable, null)
                    tv_middle.setCompoundDrawables(null, null, null, null)
                    tv_behind.setCompoundDrawables(null, null, null, null)
                }
                1 -> {
                    tv_all.setCompoundDrawables(null, null, null, null)
                    tv_front.setCompoundDrawables(null, null, null, null)
                    tv_middle.setCompoundDrawables(null, null, drawable, null)
                    tv_behind.setCompoundDrawables(null, null, null, null)
                }
                2 -> {
                    tv_all.setCompoundDrawables(null, null, null, null)
                    tv_front.setCompoundDrawables(null, null, null, null)
                    tv_middle.setCompoundDrawables(null, null, null, null)
                    tv_behind.setCompoundDrawables(null, null, drawable, null)
                }
                3 -> {
                    tv_all.setCompoundDrawables(null, null, drawable, null)
                    tv_front.setCompoundDrawables(null, null, null, null)
                    tv_middle.setCompoundDrawables(null, null, null, null)
                    tv_behind.setCompoundDrawables(null, null, null, null)
                }
            }
        }
    }

    private fun popItemClick(textView: TextView) {
        popupWindow?.dismiss()
        val drawable = textView.compoundDrawables[2]
        //此处意思是，已经勾选了，点击不做处理
        if (drawable != null) return
        tv_title.text = textView.text
        when (textView.id) {
            R.id.tv_front -> filterData(SITE_FRONT)
            R.id.tv_middle -> filterData(SITE_MIDDLE)
            R.id.tv_behind -> filterData(SITE_BEHIND)
            R.id.tv_all -> filterData()
        }
    }

    private fun filterData(site: Int = -1) {
        if (site == -1) {
            addTeamAdapter.dataList = originalDataList
        } else {
            addTeamAdapter.dataList = originalDataList.filter {
                it.site == site.toString()
            } as ArrayList<Character>
        }
    }
}