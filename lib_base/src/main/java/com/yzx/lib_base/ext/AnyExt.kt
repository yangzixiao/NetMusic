package com.yzx.lib_base.ext

import android.content.res.Resources
import androidx.annotation.StringRes

private val density = Resources.getSystem().displayMetrics.density

private val scaledDensity = Resources.getSystem().displayMetrics.scaledDensity


fun Int.dp(): Float = density * this


fun Int.sp(): Float = scaledDensity * this


fun Int.dp2px(): Float = this / density

fun Int.sp2px(): Float = this / scaledDensity

/**
 * 如果某些数据为空或者为0
 */
fun Int.defaultDes(defaultDes: String): String {

    return when {
        this <= 0 -> {
            defaultDes
        }
        this > 1000 -> {
            val handled = this.toDouble() / 10000
            val format = String.format("%.1f", handled)
            if (format.endsWith("0")) handled.toString() else format
        }
        else -> {
            this.toString()
        }
    }

}

/**
 * 如果某些数据为空或者为0
 */
fun Int.defaultDes(@StringRes defaultDes: Int): String {

    return defaultDes(simpleGetString(defaultDes))
}

/**
 * 如果某些数据为空或者为0
 */
fun Long.defaultDes(defaultDes: String): String {
    return when {
        this <= 0 -> {
            defaultDes
        }
        this > 1000 -> {
            val handled = this.toDouble() / 10000
            val format = String.format("%.1f", handled)
            if (format.endsWith("0")) handled.toString() else format
        }
        else -> {
            this.toString()
        }
    }
}

/**
 * 如果某些数据为空或者为0
 */
fun Long.defaultDes(@StringRes defaultDes: Int): String {
    return defaultDes(simpleGetString(defaultDes))
}