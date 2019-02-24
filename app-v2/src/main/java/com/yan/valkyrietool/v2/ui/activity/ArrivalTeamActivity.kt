package com.yan.valkyrietool.v2.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import com.google.gson.Gson
import com.yan.valkyrietool.v2.R
import com.yan.valkyrietool.v2.bean.Team
import com.yan.valkyrietool.v2.db.TeamDBManager
import com.yan.valkyrietool.v2.event.AddModifyTeamEvent
import com.yan.valkyrietool.v2.ui.adapter.TeamAdapter
import com.yan.webdata.bean.MainPartBean
import kotlinx.android.synthetic.main.activity_arrival_team.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

/**
 *  @author : yan
 *  @date   : 2018/7/3 12:26
 *  @desc   : 降临队伍界面
 */
class ArrivalTeamActivity : AppCompatActivity(), View.OnClickListener, Toolbar.OnMenuItemClickListener {

    private var bean: MainPartBean? = null
    private lateinit var teamAdapter: TeamAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_arrival_team)

        bean = intent.getParcelableExtra("bean")
        initView()

        loadData()
        EventBus.getDefault().register(this)
    }

    private fun initView() {
        toolbar.apply {
            title = bean?.name
            inflateMenu(R.menu.activity_team_menu)
            setOnMenuItemClickListener(this@ArrivalTeamActivity)
        }

        teamAdapter = TeamAdapter(::itemClick)
        recycler_view.apply {
            adapter = teamAdapter
            layoutManager = LinearLayoutManager(this@ArrivalTeamActivity)
        }
        tv_select_all.setOnClickListener(this)
        tv_delete.setOnClickListener(this)
    }

    private fun loadData() {
        doAsync {
            val list = query()
            runOnUiThread {
                teamAdapter.dataList = list
                tv_empty.visibility = if (list.isEmpty()) {
                    View.VISIBLE
                } else View.GONE
            }
        }
    }

    private fun query(): ArrayList<Team> {
        val list = arrayListOf<Team>()
        bean?.let {
            val teamList = TeamDBManager.query(this, it.link)
            teamList.forEach {
                val team = Gson().fromJson(it, Team::class.java)
                if (team != null) {
                    list.add(team)
                }
            }
        }
        return list
    }

    private fun itemClick(team: Team, position: Int) {
        if (ll_bottom.visibility != View.VISIBLE) {
            //对队伍进行修改
            startActivity<AddTeamActivity>("team" to team, "linkUrl" to bean?.imgUrl)
        } else {
            //底部弹出来了,进行其他编辑
            team.isSelect = !team.isSelect
            teamAdapter.notifyItemChanged(position)
        }
    }

    override fun onClick(v: View?) {
        when (v) {
            tv_select_all -> {
                if ("全选" == tv_select_all.text) {
                    tv_select_all.text = "取消全选"
                    teamAdapter.selectAll()
                } else {
                    tv_select_all.text = "全选"
                    teamAdapter.resetSelect()
                }
            }
            tv_delete -> deleteTeam()   //删除
        }
    }

    private fun deleteTeam() {
        doAsync {
            teamAdapter.dataList.forEach {
                if(it.isSelect) {
                    //需要删除,先重置，确保能在数据库找到
                    it.isSelect = false
                    deleteFromDB(it)
                }
            }
            val list = query()
            runOnUiThread {
                teamAdapter.dataList = list
                tv_empty.visibility = if (list.isEmpty()) {
                    //没有数据的时候，重置一些控件
                    tv_select_all.text = "全选"
                    ll_bottom.visibility = View.GONE
                    val editItem = toolbar.menu.findItem(R.id.edit_team)
                    editItem.title = "编辑"
                    View.VISIBLE
                } else View.GONE
            }
        }
    }

    private fun deleteFromDB(team: Team) {
        TeamDBManager.delete(this, team)
    }

    override fun onMenuItemClick(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add_team -> addTeam()
            R.id.edit_team -> editTeam(item)
        }
        return true
    }

    private fun addTeam() {
        startActivity<AddTeamActivity>("linkUrl" to bean?.link)
    }

    private fun editTeam(item: MenuItem) {
        if (teamAdapter.dataList.isEmpty()) {
            toast("没有队伍可编辑")
            return
        }
        ll_bottom.visibility = if (ll_bottom.visibility == View.VISIBLE) {
            //底部可见时，文字改为取消
            item.title = "编辑"
            //需要将所有选中的重置
            teamAdapter.resetSelect()
            View.GONE
        } else {
            item.title = "取消编辑"
            View.VISIBLE
        }
    }

    @Subscribe
    fun receiveAddTeam(event: AddModifyTeamEvent) {
        //接收到消息
        loadData()
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }
}