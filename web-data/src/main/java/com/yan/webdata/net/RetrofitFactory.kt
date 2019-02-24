package com.yan.webdata.net

import com.yan.webdata.common.DOMAIN
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

/**
 *  @author      : yan
 *  @date        : 2018/6/29 17:33
 *  @description : TODO
 */
val retrofit: Retrofit by lazy {
    Retrofit.Builder()
            .baseUrl(DOMAIN)
            .client(OkHttpClient.Builder()
//                    .addInterceptor(PreUrlInterceptor())
                    .build())
            .addConverterFactory(ScalarsConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
}