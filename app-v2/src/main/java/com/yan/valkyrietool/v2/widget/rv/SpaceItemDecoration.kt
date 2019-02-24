package com.yan.valkyrietool.v2.widget.rv

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View

/**
 *  @author : yan
 *  @date   : 2018/7/2 18:42
 *  @desc   : RecyclerView item 间距
 */
class SpaceItemDecoration(
        private val leftSpace: Int = 0, private val topSpace: Int = 0,
        private val rightSpace: Int = 0, private val bottomSpace: Int
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.left = leftSpace
        outRect.top = topSpace
        outRect.right = rightSpace
        outRect.bottom = bottomSpace
    }
}