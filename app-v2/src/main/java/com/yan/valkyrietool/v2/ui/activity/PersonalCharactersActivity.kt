package com.yan.valkyrietool.v2.ui.activity

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.TextView
import com.yan.valkyrietool.v2.R
import com.yan.valkyrietool.v2.db.PersonalCharactersDBManager
import com.yan.valkyrietool.v2.event.AddCharactersEvent
import com.yan.valkyrietool.v2.ui.adapter.AddTeamAdapter
import com.yan.valkyrietool.v2.utils.startLoading
import com.yan.valkyrietool.v2.utils.stopLoading
import com.yan.valkyrietool.v2.widget.rv.SpaceItemDecoration
import com.yan.webdata.bean.Character
import com.yan.webdata.common.CharacterConst.SITE_BEHIND
import com.yan.webdata.common.CharacterConst.SITE_FRONT
import com.yan.webdata.common.CharacterConst.SITE_MIDDLE
import kotlinx.android.synthetic.main.activity_config_characters.*
import kotlinx.android.synthetic.main.layout_loading.*
import kotlinx.android.synthetic.main.pop_site_all.view.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.jetbrains.anko.collections.forEachWithIndex
import org.jetbrains.anko.dip
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

/**
 *  @author : yan
 *  @date   : 2018/7/7 14:08
 *  @desc   : 个人角色界面
 */
class PersonalCharactersActivity : AppCompatActivity(), Toolbar.OnMenuItemClickListener, View.OnClickListener {

    companion object {
        const val ALL = -1
        const val FRONT = SITE_FRONT
        const val MIDDLE = SITE_MIDDLE
        const val BEHIND = SITE_BEHIND
    }

    private var currentSite = ALL

    private lateinit var addTeamAdapter: AddTeamAdapter

    private var originalDataList = arrayListOf<Character>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_config_characters)

        EventBus.getDefault().register(this)
        initView()
        loadAllData()
    }

    private fun initView() {
        tv_title.text = "全部"
        toolbar.apply {
            inflateMenu(R.menu.activity_personal_characters_menu)
            setOnMenuItemClickListener(this@PersonalCharactersActivity)
        }

        addTeamAdapter = AddTeamAdapter(::itemClick) {}
        recycler_view.apply {
            adapter = addTeamAdapter
            layoutManager = GridLayoutManager(this@PersonalCharactersActivity, 4)
            addItemDecoration(SpaceItemDecoration(leftSpace = dip(2), rightSpace = dip(2), bottomSpace = dip(2)))
        }
        tv_title.setOnClickListener(this)
    }

    private fun loadAllData() {
        iv_header_loading.startLoading()
        doAsync {
            originalDataList = PersonalCharactersDBManager.query(this@PersonalCharactersActivity)
            runOnUiThread {
                if (originalDataList.isEmpty()) toast("未查询到角色")
                iv_header_loading.stopLoading()
                addTeamAdapter.dataList = originalDataList
            }
        }
    }

    private fun itemClick(character: Character, position: Int) {
        character.isSelect = !character.isSelect
        addTeamAdapter.notifyItemChanged(position)
    }

    override fun onMenuItemClick(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add -> startActivity<ConfigCharactersActivity>()
            R.id.delete -> deleteCharacters()
        }
        return true
    }

    private fun deleteCharacters() {
        val tempList = originalDataList.clone() as ArrayList<Character>
        tempList.forEachWithIndex { i, character ->
            if (character.isSelect) {
                originalDataList.remove(character)
                addTeamAdapter.dataList.remove(character)
                addTeamAdapter.notifyDataSetChanged()
                PersonalCharactersDBManager.delete(this, character.linkUrl)
            }
        }
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

    @Subscribe
    fun receiveAddCharacters(event: AddCharactersEvent) {
        //查询数据库
        doAsync {
            val site: Int? = if (currentSite == ALL) null else currentSite
            originalDataList = PersonalCharactersDBManager.query(this@PersonalCharactersActivity)
            runOnUiThread {
                filterData(currentSite)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }
}