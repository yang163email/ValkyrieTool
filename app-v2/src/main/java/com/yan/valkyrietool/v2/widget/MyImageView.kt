package com.yan.valkyrietool.v2.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView

/**
 *  @author : yan
 *  @date   : 2018/7/5 9:38
 *  @desc   : todo
 */
class MyImageView : ImageView {

    var imageUrl: String? = null
    var isSelect: Boolean = false

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

}