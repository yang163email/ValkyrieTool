package com.yan.webdata.net

import com.yan.webdata.utils.insert
import okhttp3.Interceptor
import okhttp3.Response

/**
 *  @author      : yan
 *  @date        : 2018/6/29 18:02
 *  @description : url拦截，在url前面添加  zh_CN/
 */
class PreUrlInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val url = originalRequest.url().toString()
        //abc.com/
        val searchStr = ".com/"
        val index = url.indexOf(searchStr) + searchStr.length
        val newUrl = url.insert("zh_CN/", index)
        val request = originalRequest.newBuilder().url(newUrl).build()

        return chain.proceed(request)
    }
}