package com.yzx.lib_base.ext

import android.content.res.Resources
import androidx.annotation.StringRes

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

/**
 * 如果某些数据为空或者为0
 */
fun Int.defaultDes(defaultDes: String): String {

    return if (this == 0) defaultDes else this.toString()
}

/**
 * 如果某些数据为空或者为0
 */
fun Int.defaultDes(@StringRes defaultDes: Int): String {

    return defaultDes(getString(defaultDes))
}

/**
 * 如果某些数据为空或者为0
 */
fun Long.defaultDes(defaultDes: String): String {
    return if (this == 0L) defaultDes else this.toString()
}

/**
 * 如果某些数据为空或者为0
 */
fun Long.defaultDes(@StringRes defaultDes: Int): String {
    return defaultDes(getString(defaultDes))
}