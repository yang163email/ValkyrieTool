package com.yan.webdata.service

import com.yan.webdata.net.retrofit
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

/**
 *  @author      : yan
 *  @date        : 2018/6/29 17:41
 *  @description : 降临相关api
 */
interface ArrivalApi {

    @GET("/zh_CN/connect_list.html")
    fun getMain(): Call<String>

    @GET
    fun getArrivalDetail(@Url url: String): Call<String>
}

object ArrivalService: ArrivalApi by retrofit.create(ArrivalApi::class.java)