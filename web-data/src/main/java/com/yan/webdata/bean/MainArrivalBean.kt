package com.yan.webdata.bean

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class MainArrivalBean(
        val titleImg: String,    //title图标
        val partList: ArrayList<MainPartBean>,
        val typeName: String       //类型：1：超绝降临；2：超降临；3：降临
)

@Parcelize
data class MainPartBean(
        val link: String,   //链接地址
        val imgUrl: String, //图片地址
        val name: String   //名称
) : Parcelable