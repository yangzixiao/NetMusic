package com.yzx.lib_base.ext

import android.content.res.Resources
import android.util.TypedValue
import androidx.annotation.StringRes

private val density = Resources.getSystem().displayMetrics.density

private val scaledDensity = Resources.getSystem().displayMetrics.scaledDensity


val Float.dp
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this,
        Resources.getSystem().displayMetrics
    )

val Float.sp
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_SP,
        this,
        Resources.getSystem().displayMetrics
    )


fun Int.px2dp(): Float = this / density

fun Int.px2sp(): Float = this / scaledDensity


fun Int.format(): String {
    return when {
        this > 10000 -> {
            val handled = this.toDouble() / 10000
            val format = String.format("%.1f", handled)
            "${if (format.endsWith("0")) handled.toString() else format}万"
        }
        else ->
            this.toString()
    }
}

/**
 * 如果某些数据为空或者为0
 */
fun Int.defaultDes(defaultDes: String): String {
    return when {
        this <= 0 -> {
            defaultDes
        }
        else -> {
            this.format()
        }
    }

}

/**
 * 如果某些数据为空或者为0
 */
fun Int.defaultDes(@StringRes defaultDes: Int): String {
    return defaultDes(stringOf(defaultDes))
}


fun Long.format(): String {
    return when {
        this > 10000 -> {
            val handled = this.toDouble() / 10000
            val format = String.format("%.1f", handled)
            "${if (format.endsWith("0")) handled.toString() else format}万"
        }
        else ->
            this.toString()
    }
}
/**
 * 如果某些数据为空或者为0
 */
fun Long.defaultDes(defaultDes: String): String {
    return when {
        this <= 0 -> {
            defaultDes
        }
        else -> {
            this.format()
        }
    }
}

/**
 * 如果某些数据为空或者为0
 */
fun Long.defaultDes(@StringRes defaultDes: Int): String {
    return defaultDes(stringOf(defaultDes))
}