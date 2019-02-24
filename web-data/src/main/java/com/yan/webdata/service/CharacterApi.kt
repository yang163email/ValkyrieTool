package com.yan.webdata.service

import com.yan.webdata.net.retrofit
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 *  @author      : yan
 *  @date        : 2018/6/29 17:41
 *  @description : 角色相关api
 */
interface CharacterApi {

    @GET("/zh_CN/character_list/{index}.html")
    fun getCharacters(@Path("index") index: Int): Call<String>
}

object CharacterService: CharacterApi by retrofit.create(CharacterApi::class.java)