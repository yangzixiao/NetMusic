package com.yzx.module_main.widget

import android.content.Context
import com.yzx.lib_magic_indicator.buildins.ArgbEvaluatorHolder
import com.yzx.lib_magic_indicator.buildins.commonnavigator.titles.SimplePagerTitleView

/**
 * @author yzx
 * @date 2020/5/8
 * Description
 */
class MainTitleView(context: Context) : SimplePagerTitleView(context) {
    private var mMinScale = 0.75f

    override fun onLeave(index: Int, totalCount: Int, leavePercent: Float, leftToRight: Boolean) {
        scaleX = 1.0f + (mMinScale - 1.0f) * leavePercent
        scaleY = 1.0f + (mMinScale - 1.0f) * leavePercent


        val color = ArgbEvaluatorHolder.eval(leavePercent, selectedColor, normalColor)

        setTextColor(color)

    }

    override fun onEnter(index: Int, totalCount: Int, enterPercent: Float, leftToRight: Boolean) {
        scaleX = mMinScale + (1.0f - mMinScale) * enterPercent
        scaleY = mMinScale + (1.0f - mMinScale) * enterPercent


        val color =
            ArgbEvaluatorHolder.eval(enterPercent, normalColor, selectedColor)
        setTextColor(color)
    }

    override fun onSelected(index: Int, totalCount: Int) {

    }

    override fun onDeselected(index: Int, totalCount: Int) {

    }

    fun getMinScale(): Float {
        return mMinScale
    }

    fun setMinScale(minScale: Float) {
        mMinScale = minScale
    }
}