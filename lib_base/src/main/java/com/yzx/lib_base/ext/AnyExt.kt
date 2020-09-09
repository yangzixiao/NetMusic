package com.yzx.lib_base.ext

import android.content.res.Resources

private val density = Resources.getSystem().displayMetrics.density

private val scaledDensity = Resources.getSystem().displayMetrics.scaledDensity


fun Int.dp(): Float {
    return density * this
}


fun Int.sp(): Float {
    return scaledDensity * this
}


fun Int.dp2px(): Float {
    return this / density
}

fun Int.sp2px(): Float {
    return this / scaledDensity
}