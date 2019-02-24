package com.yan.valkyrietool.v2.utils

import android.view.View
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import android.widget.EditText
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

fun ImageView.glideCenterCrop(url: String) {
    Glide.with(context).load(url).centerCrop().into(this)
}

fun ImageView.startLoading(duration: Long = 1200) {
    val rotateAnimation = RotateAnimation(
            0f, 360f,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
    )
    rotateAnimation.duration = duration
    rotateAnimation.interpolator = LinearInterpolator()
    rotateAnimation.repeatMode = Animation.RESTART
    rotateAnimation.repeatCount = Animation.INFINITE
    visibility = View.VISIBLE
    startAnimation(rotateAnimation)
}

fun ImageView.stopLoading() {
    visibility = View.GONE
    clearAnimation()
}

val EditText.content: String
    get() = text.trim().toString()
