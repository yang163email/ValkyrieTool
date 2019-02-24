package com.yan.valkyrietool.v2.widget

import android.app.Activity
import android.content.Context
import android.os.Build
import android.support.v7.widget.Toolbar
import android.util.AttributeSet
import android.view.ContextThemeWrapper
import android.view.View
import com.yan.valkyrietool.v2.R
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
        popupTheme = R.style.OverflowMenuStyle
    }

    override fun onClick(v: View) {
        when (context) {
            is Activity -> (context as Activity).finish()
            is ContextThemeWrapper -> {
                val baseContext = (context as ContextThemeWrapper).baseContext
                if (baseContext is Activity) {
                    baseContext.finish()
                }
            }
            is android.support.v7.view.ContextThemeWrapper -> {
                val baseContext = (context as android.support.v7.view.ContextThemeWrapper).baseContext
                if (baseContext is Activity) {
                    baseContext.finish()
                }
            }
        }
    }

}
