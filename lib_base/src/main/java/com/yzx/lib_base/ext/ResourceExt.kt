package com.yzx.lib_base.ext

import android.content.res.Resources

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

//fun getDefaultToolbarHeight(): Int {
//    val reSource = getReSource()
//    var result = 64.dp().toInt()
//    val resourceId =
//        reSource.getIdentifier("actionBarSize", "dimen", "android")
//    if (resourceId > 0) {
//        result = reSource.getDimensionPixelSize(resourceId)
//    }
//    return result
//}

