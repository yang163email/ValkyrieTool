package com.yan.valkyrietool.utils

import android.widget.ImageView
import com.bumptech.glide.Glide

/**
 *  @author : yan
 *  @date   : 2018/6/28 16:29
 *  @desc   : todo
 */
fun ImageView.glide(url: String) {
    Glide.with(context).load(url).into(this)
}