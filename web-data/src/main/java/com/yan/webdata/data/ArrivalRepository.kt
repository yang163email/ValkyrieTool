package com.yan.webdata.data

import android.util.Log
import com.yan.webdata.service.ArrivalService
import retrofit2.Response
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter

/**
 *  @author      : yan
 *  @date        : 2018/7/2 13:13
 *  @description : 降临数据仓库
 */
class ArrivalRepository {

    protected val TAG = javaClass.simpleName

    /**
     * 获取主页源数据
     * 同步执行，未做线程切换
     */
    fun getMainOriginal(saved: Boolean = false): String {
        val call = ArrivalService.getMain()
        val response = call.execute()

        return handleResponse(response, saved, "main")
    }

    private fun handleResponse(response: Response<String>, saved: Boolean, subDir: String): String {
        Log.d(TAG, "handleResponse(): $response")

        val string = response.body()
        if (saved) {
            val url = response.raw().request().url().toString()
            val childName = url.split('/').last()
            string?.let {
                val file = File("netRes/$subDir")
                if (!file.exists()) file.mkdirs()
                val bw = BufferedWriter(FileWriter(File(file, childName)))
                bw.write(it)
                bw.close()
            }
        }
        return string ?: ""
    }

    fun getDetailOriginal(url: String, saved: Boolean = true) {
        val call = ArrivalService.getArrivalDetail(url)
        val response = call.execute()

        handleResponse(response, saved, "detail")
    }
}