package com.yzx.lib_base.ext

import android.content.res.Resources
import android.graphics.drawable.Drawable
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.yzx.lib_base.manager.AppManager

fun getReSource(): Resources {
    return Resources.getSystem()
}

fun getDefaultStatusHeight(): Int {
    var result = 25.dp().toInt()
    val reSource = getReSource()
    val resourceId =
        reSource.getIdentifier("status_bar_height", "dimen", "android")
    if (resourceId > 0) {
        result = reSource.getDimensionPixelSize(resourceId)
    }
    return result
}

private fun application() = AppManager.application

fun getString(@StringRes stringResources: Int): String {
    return application().getString(stringResources)
}


fun getColor(@ColorRes colorRes: Int): Int {
    return ContextCompat.getColor(application(), colorRes)
}

fun getDrawable(@DrawableRes drawableRes: Int): Drawable {
    return ContextCompat.getDrawable(application(), drawableRes)!!
}
