package com.yan.valkyrietool.widget

import android.app.Activity
import android.content.Context
import android.os.Build
import android.support.v7.widget.Toolbar
import android.util.AttributeSet
import android.view.View
import com.yan.valkyrietool.R
import org.jetbrains.anko.dip

/**
 * @author : yan
 * @date : 2018/6/28 18:37
 * @desc : 自定义toolbar
 */
class CustomToolbar : Toolbar, View.OnClickListener {

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.CustomToolbar)
        val showBackIcon = ta.getBoolean(R.styleable.CustomToolbar_showBackIcon, false)
        ta.recycle()

        if (showBackIcon) {
            setNavigationIcon(R.mipmap.ic_back)
            setNavigationOnClickListener(this)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            elevation = dip(4).toFloat()
        }
        setTitleTextAppearance(context, R.style.Toolbar_TitleText)
        setTitleTextColor(resources.getColor(R.color.c_ffffff))
        setBackgroundColor(resources.getColor(R.color.colorPrimary))
    }

    override fun onClick(v: View) {
        if (context is Activity) {
            (context as Activity).finish()
        }
    }
}
