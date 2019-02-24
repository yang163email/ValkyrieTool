package com.yan.valkyrietool.v2.ui.activity

import android.graphics.Color
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import com.yan.valkyrietool.v2.R
import com.yan.valkyrietool.v2.base.BaseActivity
import com.yan.valkyrietool.v2.ui.adapter.MainAdapter
import com.yan.valkyrietool.v2.utils.startLoading
import com.yan.valkyrietool.v2.utils.stopLoading
import com.yan.webdata.bean.MainArrivalBean
import com.yan.webdata.data.ArrivalDataConverter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_loading.*
import org.jetbrains.anko.startActivity

class MainActivity : BaseActivity(), SwipeRefreshLayout.OnRefreshListener, Toolbar.OnMenuItemClickListener {

    private lateinit var mainAdapter: MainAdapter

    private var isLoading = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
        loadData()
    }

    private fun initView() {
        toolbar.inflateMenu(R.menu.activity_main_menu)
        toolbar.setOnMenuItemClickListener(this)
        refresh_layout.apply {
            setColorSchemeColors(Color.BLACK, Color.RED, Color.BLUE)
            setOnRefreshListener(this@MainActivity)
        }

        mainAdapter = MainAdapter()
        recycler_view.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = mainAdapter
        }
    }

    private fun loadData() {
        iv_header_loading.startLoading()
        ArrivalDataConverter.getRxMainData(object : BaseObserver<ArrayList<MainArrivalBean>>(this) {
            override fun onNext(t: ArrayList<MainArrivalBean>) {
                mainAdapter.dataList = t
            }
        })
    }

    override fun hideLoading() {
        isLoading = false
        if (refresh_layout.isRefreshing) {
            refresh_layout.isRefreshing = false
        }
        iv_header_loading.stopLoading()
    }

    override fun onRefresh() {
        if (isLoading) {
            refresh_layout.isRefreshing = false
        } else {
            loadData()
        }
    }

    override fun onMenuItemClick(item: MenuItem): Boolean {
        startActivity<SettingActivity>()
        return true
    }
}
