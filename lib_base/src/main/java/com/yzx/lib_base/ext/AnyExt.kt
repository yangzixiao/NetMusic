package com.yzx.lib_base.ext

import android.content.res.Resources

private val density = Resources.getSystem().displayMetrics.density

private val scaledDensity = Resources.getSystem().displayMetrics.scaledDensity

fun Int.dp(value: Float): Float {
    return density * value
}


fun Int.sp(value: Float): Float {
    return scaledDensity * value
}


fun Int.dp2px(value: Float): Float {
    return value / density
}

fun Int.sp2px(value: Float): Float {
    return value / scaledDensity
}