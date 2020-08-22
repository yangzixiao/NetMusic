package com.yzx.lib_base.base

import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat

/**
 * @author yzx
 * @date 2020/4/8
 * Description
 */
open class BaseActivity<VM : BaseViewModel> : UIActivity<VM>() {


    fun simpleGetColor(@ColorRes color: Int):Int{
        return ContextCompat.getColor(this,color)
    }

}