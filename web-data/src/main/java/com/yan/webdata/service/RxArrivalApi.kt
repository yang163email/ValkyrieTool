package com.yan.webdata.service

import com.yan.webdata.net.retrofit
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Url

/**
 *  @author      : yan
 *  @date        : 2018/6/29 17:41
 *  @description : 降临相关api
 */
interface RxArrivalApi {

    @GET("/zh_CN/connect_list.html")
    fun getMain(): Observable<String>

    @GET
    fun getArrivalDetail(@Url url: String): Observable<String>
}

object RxArrivalService: RxArrivalApi by retrofit.create(RxArrivalApi::class.java)