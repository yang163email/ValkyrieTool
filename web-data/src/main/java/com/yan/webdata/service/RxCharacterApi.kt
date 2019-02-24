package com.yan.webdata.service

import com.yan.webdata.net.retrofit
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

/**
 *  @author      : yan
 *  @date        : 2018/6/29 17:41
 *  @description : 角色相关api
 */
interface RxCharacterApi {

    @GET("/zh_CN/character_list/{index}.html")
    fun getCharacters(@Path("index") index: Int): Observable<String>
}

object RxCharacterService: RxCharacterApi by retrofit.create(RxCharacterApi::class.java)