package com.yan.valkyrietool.v2

import android.app.Application
import com.tencent.bugly.crashreport.CrashReport

/**
 *  @author : yan
 *  @date   : 2018/7/10 9:44
 *  @desc   : todo
 */
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        CrashReport.initCrashReport(applicationContext, "1243d595b4", BuildConfig.DEBUG)
    }
}