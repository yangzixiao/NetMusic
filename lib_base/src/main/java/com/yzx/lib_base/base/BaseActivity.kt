package com.yzx.lib_base.base

import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import com.yzx.lib_base.mvvm.MvvmActivity

/**
 * @author yzx
 * @date 2020/4/8
 * Description
 */
open class BaseActivity : MvvmActivity() {


    fun simpleGetColor(@ColorRes color: Int):Int{
        return ContextCompat.getColor(this,color)
    }

}