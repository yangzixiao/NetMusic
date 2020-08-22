package com.yzx.module_main.widget

import android.content.Context
import androidx.core.content.ContextCompat
import com.yzx.module_main.R

import net.lucode.hackware.magicindicator.buildins.ArgbEvaluatorHolder
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView

/**
 * @author yzx
 * @date 2020/5/8
 * Description
 */
class MainTitleView(context: Context) : SimplePagerTitleView(context) {
    private var mMinScale = 0.75f
    private val colorWhite = getColor(R.color.colorWhite)
    private val colorDarkWhite = getColor(R.color.colorDarkWhite)
    private val colorBlack = getColor(R.color.colorBlack)
    private val colorLightBlack = getColor(R.color.colorLightBlack)

    private fun getColor(color:Int):Int{
        return ContextCompat.getColor(context,color)
    }
    override fun onLeave(
        index: Int,
        totalCount: Int,
        leavePercent: Float,
        leftToRight: Boolean
    ) {


        scaleX = 1.0f + (mMinScale - 1.0f) * leavePercent
        scaleY = 1.0f + (mMinScale - 1.0f) * leavePercent


        val color = if (index == 0) {
            ArgbEvaluatorHolder.eval(leavePercent, colorWhite, colorLightBlack)
        } else if (index == 1) {
            if (leftToRight) {
                ArgbEvaluatorHolder.eval(leavePercent, colorBlack, colorLightBlack)
            } else {
                ArgbEvaluatorHolder.eval(leavePercent, colorBlack, colorDarkWhite)
            }
        } else {
            ArgbEvaluatorHolder.eval(
                leavePercent,
                colorBlack,
                colorLightBlack
            )
        }

        setTextColor(color)

    }

    override fun onEnter(
        index: Int,
        totalCount: Int,
        enterPercent: Float,
        leftToRight: Boolean
    ) {

        scaleX = mMinScale + (1.0f - mMinScale) * enterPercent
        scaleY = mMinScale + (1.0f - mMinScale) * enterPercent


        val color = if (index == 0) {
            ArgbEvaluatorHolder.eval(enterPercent, colorLightBlack, colorWhite)
        } else if (index == 1) {
            if (leftToRight) {
                ArgbEvaluatorHolder.eval(enterPercent, colorLightBlack, colorBlack)
            } else {
                ArgbEvaluatorHolder.eval(enterPercent, colorLightBlack, colorBlack)
            }
        } else {
            ArgbEvaluatorHolder.eval(enterPercent, colorLightBlack, colorBlack)
        }

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