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
import com.yan.valkyrietool.v2.bean.Team
import com.yan.valkyrietool.v2.bean.Teams
import com.yan.valkyrietool.v2.db.PersonalCharactersDBManager
import com.yan.valkyrietool.v2.db.TeamDBManager
import com.yan.valkyrietool.v2.event.AddModifyTeamEvent
import com.yan.valkyrietool.v2.ui.adapter.AddTeamAdapter
import com.yan.valkyrietool.v2.utils.*
import com.yan.valkyrietool.v2.widget.MyImageView
import com.yan.valkyrietool.v2.widget.rv.SpaceItemDecoration
import com.yan.webdata.bean.Character
import com.yan.webdata.common.CharacterConst.SITE_BEHIND
import com.yan.webdata.common.CharacterConst.SITE_FRONT
import com.yan.webdata.common.CharacterConst.SITE_MIDDLE
import com.yan.webdata.data.CharacterDataConverter
import kotlinx.android.synthetic.main.activity_add_team.*
import kotlinx.android.synthetic.main.dialog_add_team.view.*
import kotlinx.android.synthetic.main.layout_loading.*
import kotlinx.android.synthetic.main.pop_site.view.*
import org.greenrobot.eventbus.EventBus
import org.jetbrains.anko.*

/**
 *  @author : yan
 *  @date   : 2018/7/4 15:13
 *  @desc   : 添加队伍界面
 */
class AddTeamActivity : BaseActivity(), Toolbar.OnMenuItemClickListener, View.OnClickListener {

    companion object {
        private const val DEFAULT_IMAGE = "defalut"
    }

    private var linkUrl: String? = null
    private var teamBean: Team? = null
    private var originalId = -1
    private lateinit var addTeamAdapter: AddTeamAdapter
    private val pinyin4j by lazy { Pinyin4j() }

    private var originalDataList = arrayListOf<Character>()

    private val team = ArrayList<Character>(5)

    //数据库是否有配置角色库
    private var hasDBData = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_team)

        initView()

        linkUrl = intent.getStringExtra("linkUrl")
        teamBean = intent.getParcelableExtra<Team>("team")

        initTeamData()
        loadDBData()
    }

    /**
     * 加载数据前，初始化队伍数据
     */
    private fun initTeamData() {
        teamBean?.let {
            //查找之前，选找到当前数据id，避免后面修改找不到
            originalId = TeamDBManager.queryId(this, it)

            val characters = it.characters
            initTeamDataTemp(iv_avatar1, characters[0])
            initTeamDataTemp(iv_avatar2, characters[1])
            initTeamDataTemp(iv_avatar3, characters[2])
            initTeamDataTemp(iv_avatar4, characters[3])
            initTeamDataTemp(iv_avatar5, characters[4])
            team.addAll(characters)
        }
    }

    private fun initTeamDataTemp(imageView: MyImageView, character: Character) {
        imageView.glide(character.avatar)
        imageView.imageUrl = character.avatar
        imageView.isSelect = character.isSelect
    }

    private fun initView() {
        tv_title.text = "近战"
        toolbar.apply {
            inflateMenu(R.menu.activity_add_team_menu)
            val item = menu.findItem(R.id.search)
            val searchView = item.actionView as SearchView
            initSearchView(searchView)
            setOnMenuItemClickListener(this@AddTeamActivity)
        }

        addTeamAdapter = AddTeamAdapter(::itemClick, ::itemLongClick)
        recycler_view.apply {
            adapter = addTeamAdapter
            layoutManager = GridLayoutManager(this@AddTeamActivity, 4)
            addItemDecoration(SpaceItemDecoration(leftSpace = dip(2), rightSpace = dip(2), bottomSpace = dip(2)))
        }
        tv_title.setOnClickListener(this)
        iv_avatar1.setOnClickListener(this)
        iv_avatar2.setOnClickListener(this)
        iv_avatar3.setOnClickListener(this)
        iv_avatar4.setOnClickListener(this)
        iv_avatar5.setOnClickListener(this)
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
            val filterList = originalDataList.filter {
                it.name.contains(newText) || getPinyin(it.name).contains(newText, true)
            } as ArrayList<Character>
            addTeamAdapter.dataList = filterList
        }
    }

    private fun getPinyin(str: String): String {
        return pinyin4j.toPinYinUppercase(str)
    }

    private fun resetDataList() {
        addTeamAdapter.dataList = originalDataList
    }

    private fun loadDBData() {
        iv_header_loading.startLoading()
        val dataList = PersonalCharactersDBManager.query(this@AddTeamActivity, SITE_FRONT)
        if (dataList.isEmpty()) {
            loadNetData(SITE_FRONT)
        } else {
            hasDBData = true
            originalDataList = dataList
            //读取底部队伍数据，并配置
            changeOriginalData(originalDataList)
            addTeamAdapter.dataList = originalDataList
            hideLoading()
        }
    }

    private fun loadNetData(site: Int) {
        CharacterDataConverter.getRxCharacters(site, object : BaseObserver<ArrayList<Character>>(this@AddTeamActivity) {
            override fun onNext(t: ArrayList<Character>) {
                //读取底部队伍数据，并配置
                originalDataList = t
                changeOriginalData(originalDataList)
                addTeamAdapter.dataList = originalDataList
            }
        })
    }

    override fun hideLoading() {
        iv_header_loading.stopLoading()
    }

    private fun loadData(site: Int = SITE_FRONT) {
        iv_header_loading.startLoading()
        if (hasDBData) {
            originalDataList = PersonalCharactersDBManager.query(this@AddTeamActivity, site)
            //读取底部队伍数据，并配置
            changeOriginalData(originalDataList)
            addTeamAdapter.dataList = originalDataList
            iv_header_loading.stopLoading()
        } else {
            loadNetData(site)
        }
    }

    /**
     * 读取底部队伍数据，并配置
     */
    private fun changeOriginalData(characters: ArrayList<Character>) {
        searchTeamAndChange(iv_avatar1, characters)
        searchTeamAndChange(iv_avatar2, characters)
        searchTeamAndChange(iv_avatar3, characters)
        searchTeamAndChange(iv_avatar4, characters)
        searchTeamAndChange(iv_avatar5, characters)
    }

    private fun searchTeamAndChange(imageView: MyImageView, characters: ArrayList<Character>) {
        if (imageView.isSelect) {
            //已经有数据，遍历查找
            for ((index, character) in characters.withIndex()) {
                if (character.avatar == imageView.imageUrl) {
                    character.isSelect = true
                    addTeamAdapter.notifyItemChanged(index)
                    break
                }
            }
        }
    }

    private fun itemClick(character: Character, position: Int) {
        val select = character.isSelect
        val avatar = character.avatar

        val teamSize = team.size
        //此处判断是否之前有选过
        if (avatar == iv_avatar1.imageUrl) {
            resetImg(iv_avatar1, avatar)
        } else if (avatar == iv_avatar2.imageUrl) {
            resetImg(iv_avatar2, avatar)
        } else if (avatar == iv_avatar3.imageUrl) {
            resetImg(iv_avatar3, avatar)
        } else if (avatar == iv_avatar4.imageUrl) {
            resetImg(iv_avatar4, avatar)
        } else if (avatar == iv_avatar5.imageUrl) {
            resetImg(iv_avatar5, avatar)
        } else if (teamSize == 5 && !select) {
            toast("已经满了5个")
            return
        } else if (teamSize < 5) {
            //添加到team中,并且排序
            if (!team.contains(character)) {
                team.add(character)
                team.sortBy { it.siteSort }
            }
        }
        character.isSelect = !select

        //重新设置排序
        showBottomTeam()
    }

    /**
     * 显示底部配队
     */
    private fun showBottomTeam() {
        if (team.size > 5) {
            toast("error，队伍角色>5")
            return
        }

        when (team.size) {
            1 -> {
                setImage(iv_avatar1, team[0])
                resetImg(iv_avatar2)
                resetImg(iv_avatar3)
                resetImg(iv_avatar4)
                resetImg(iv_avatar5)
            }
            2 -> {
                setImage(iv_avatar1, team[0])
                setImage(iv_avatar2, team[1])
                resetImg(iv_avatar3)
                resetImg(iv_avatar4)
                resetImg(iv_avatar5)
            }
            3 -> {
                setImage(iv_avatar1, team[0])
                setImage(iv_avatar2, team[1])
                setImage(iv_avatar3, team[2])
                resetImg(iv_avatar4)
                resetImg(iv_avatar5)
            }
            4 -> {
                setImage(iv_avatar1, team[0])
                setImage(iv_avatar2, team[1])
                setImage(iv_avatar3, team[2])
                setImage(iv_avatar4, team[3])
                resetImg(iv_avatar5)
            }
            5 -> {
                setImage(iv_avatar1, team[0])
                setImage(iv_avatar2, team[1])
                setImage(iv_avatar3, team[2])
                setImage(iv_avatar4, team[3])
                setImage(iv_avatar5, team[4])
            }
        }
    }

    /**
     * 设置底部图片
     */
    private fun setImage(imageView: MyImageView, character: Character) {
        imageView.glide(character.avatar)
        imageView.imageUrl = character.avatar
        imageView.isSelect = character.isSelect
    }

    /**
     * 重置图片
     */
    private fun resetImg(imageView: MyImageView, linkUrl: String? = null) {
        imageView.setImageResource(R.mipmap.ic_launcher)
        imageView.imageUrl = DEFAULT_IMAGE
        imageView.isSelect = false
        //删除
        val character = team.find { it.avatar == linkUrl }
        team.remove(character)
    }

    private fun itemLongClick(character: Character) {
        val name = character.name
        val link = character.linkUrl
        startActivity<CharacterDetailActivity>(
                "name" to name,
                "linkUrl" to link
        )
    }

    override fun onClick(v: View?) {
        when (v) {
            tv_title -> showPopupWindow()
            iv_avatar1 -> resetSingleImg(iv_avatar1)
            iv_avatar2 -> resetSingleImg(iv_avatar2)
            iv_avatar3 -> resetSingleImg(iv_avatar3)
            iv_avatar4 -> resetSingleImg(iv_avatar4)
            iv_avatar5 -> resetSingleImg(iv_avatar5)
        }
    }

    private var popupWindow: PopupWindow? = null
    private var popupView: View? = null

    private fun showPopupWindow() {
        if (popupWindow == null) {
            popupView = layoutInflater.inflate(R.layout.pop_site, null)
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
            }
            tv_front.setOnClickListener {
                popItemClick(tv_front)
            }
            tv_middle.setOnClickListener {
                popItemClick(tv_middle)
            }
            tv_behind.setOnClickListener {
                popItemClick(tv_behind)
            }

        }
        popupWindow?.showAsDropDown(tv_title)
    }

    private fun popItemClick(textView: TextView) {
        popupWindow?.dismiss()
        val drawable = textView.compoundDrawables[2]
        //此处意思是，已经勾选了，点击不做处理
        if (drawable != null) return
        tv_title.text = textView.text
        when (textView.id) {
            R.id.tv_front -> loadData(SITE_FRONT)
            R.id.tv_middle -> loadData(SITE_MIDDLE)
            R.id.tv_behind -> loadData(SITE_BEHIND)
        }
    }

    private fun selectPopView(position: Int) {
        popupView?.apply {
            val drawable = resources.getDrawable(R.mipmap.right)
            drawable.setBounds(0, 0, drawable.minimumWidth, drawable.minimumHeight)
            when (position) {
                0 -> {
                    tv_front.setCompoundDrawables(null, null, drawable, null)
                    tv_middle.setCompoundDrawables(null, null, null, null)
                    tv_behind.setCompoundDrawables(null, null, null, null)
                }
                1 -> {
                    tv_front.setCompoundDrawables(null, null, null, null)
                    tv_middle.setCompoundDrawables(null, null, drawable, null)
                    tv_behind.setCompoundDrawables(null, null, null, null)
                }
                2 -> {
                    tv_front.setCompoundDrawables(null, null, null, null)
                    tv_middle.setCompoundDrawables(null, null, null, null)
                    tv_behind.setCompoundDrawables(null, null, drawable, null)
                }
            }
        }

    }

    private fun resetSingleImg(imageView: MyImageView) {
        val imageUrl = imageView.imageUrl
        if (imageUrl == null || imageUrl == DEFAULT_IMAGE) {
            return
        }
        for ((position, character) in addTeamAdapter.dataList.withIndex()) {
            if (character.avatar == imageUrl) {
                character.isSelect = false
                addTeamAdapter.notifyItemChanged(position)
                break
            }
        }
        resetImg(imageView, imageView.imageUrl)
        //点击完毕，重新设置队伍
        showBottomTeam()
    }

    override fun onMenuItemClick(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.confirm -> {
                //确定，进行保存
                clickConfirm()
            }
        }

        return true
    }

    private fun clickConfirm() {
        if (team.size < 5) {
            toast("请配置5人队伍")
            return
        }
        alert("设置备注") {
            val view = layoutInflater.inflate(R.layout.dialog_add_team, null)
            view.et_content.setText(teamBean?.desc)
            customView = view
            yesButton {
                saveTeam(view.et_content.content)
            }
            noButton {}
        }.show()
    }

    private fun saveTeam(desc: String) {
        linkUrl?.let {
            val teams = Teams(it, Team(team, desc))
            if (originalId != -1) {
                //表示数据库原本存在
                val result = TeamDBManager.update(this, teams, originalId)
                if (result) {
                    //成功
                    EventBus.getDefault().post(AddModifyTeamEvent())
                    toast("修改成功")
                    finish()
                } else {
                    toast("修改失败")
                }
            } else {
                val result = TeamDBManager.insert(this, teams)
                if (result) {
                    //成功
                    EventBus.getDefault().post(AddModifyTeamEvent())
                    toast("添加成功")
                    finish()
                } else {
                    toast("添加失败")
                }
            }
        }
    }

}