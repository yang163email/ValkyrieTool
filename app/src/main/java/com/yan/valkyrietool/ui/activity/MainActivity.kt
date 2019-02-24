package com.yan.valkyrietool.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.yan.valkyrietool.R
import com.yan.valkyrietool.repository.GlobalRepository
import com.yan.valkyrietool.ui.adapter.MainAdapter
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.doAsync
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    private lateinit var mainAdapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar
        mainAdapter = MainAdapter()
        recycler_view.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = mainAdapter
        }
        initData()
    }

    private fun initData() {
        doAsync {
            val data = GlobalRepository.getMainTotalData2()
            runOnUiThread {
                mainAdapter.dataList = data
            }
        }
    }

}
