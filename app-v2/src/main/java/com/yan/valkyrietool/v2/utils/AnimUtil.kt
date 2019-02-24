package com.yan.valkyrietool.v2.utils

import android.view.View
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.ImageView

/**
 *  @author : yan
 *  @date   : 2018/7/6 10:23
 *  @desc   : todo
 */
object AnimUtil {

    fun startLoading(imageView: ImageView, duration: Long = 1500) {
        val rotateAnimation = RotateAnimation(
                0f, 360f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
        )
        rotateAnimation.duration = duration
        rotateAnimation.repeatMode = Animation.RESTART
        rotateAnimation.repeatCount = Animation.INFINITE
        imageView.visibility = View.VISIBLE
        imageView.startAnimation(rotateAnimation)
    }

    fun stopLoading(imageView: ImageView) {
        imageView.visibility = View.VISIBLE
        imageView.clearAnimation()
    }
}