package com.yzx.lib_base.base

import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import com.yzx.lib_base.mvvm.UIActivity

/**
 * @author yzx
 * @date 2020/4/8
 * Description
 */
open class BaseActivity : UIActivity() {


    fun simpleGetColor(@ColorRes color: Int):Int{
        return ContextCompat.getColor(this,color)
    }

}