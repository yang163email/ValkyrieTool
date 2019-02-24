package com.yan.webdata.data

import android.util.Log
import com.yan.webdata.service.CharacterService
import retrofit2.Response
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter

/**
 *  @author      : yan
 *  @date        : 2018/7/2 13:13
 *  @description : 角色数据仓库
 */
class CharacterRepository {

    protected val TAG = javaClass.simpleName

    /**
     * 获取角色源数据
     * 一共3页
     * first:原始url
     * second:数据string
     */
    fun getCharacters(site: Int, saved: Boolean = false): Pair<String, String> {
        val call = CharacterService.getCharacters(site)
        val response = call.execute()
        val url = response.raw().request().url().toString()
        val str = handleResponse(response, saved, "character")
        return Pair(url, str)
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
}