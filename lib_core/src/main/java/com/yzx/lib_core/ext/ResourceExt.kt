package com.yzx.lib_core.ext

import android.content.res.Resources
import android.graphics.drawable.Drawable
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.yzx.lib_core.manager.AppManager

fun getReSource(): Resources {
    return Resources.getSystem()
}

fun getDefaultStatusHeight(): Int {
    var result = 25f.dp.toInt()
    val reSource = getReSource()
    val resourceId =
        reSource.getIdentifier("status_bar_height", "dimen", "android")
    if (resourceId > 0) {
        result = reSource.getDimensionPixelSize(resourceId)
    }
    return result
}


private fun application() = AppManager.application
fun getScreenWidth():Int{
   return application().resources.displayMetrics.widthPixels
}

fun stringOf(@StringRes vararg stringRes: Int): String {
    var result = ""
    stringRes.forEach {
        result += application().getString(it)
    }
    return result
}


fun colorOf(@ColorRes colorRes: Int): Int {
    return ContextCompat.getColor(application(), colorRes)
}

fun drawableOf(@DrawableRes drawableRes: Int): Drawable {
    return ContextCompat.getDrawable(application(), drawableRes)!!
}
