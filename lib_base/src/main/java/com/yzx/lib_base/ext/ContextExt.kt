package com.yzx.lib_base.ext

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.widget.Toast
import androidx.annotation.StringRes
import com.bumptech.glide.load.engine.Resource
import com.yzx.lib_base.R


fun Context.toast(msg: String) {
    if (msg.isEmpty()) {
        return
    }
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

fun Context.toast(@StringRes msg: Int) {
    toast(simpleGetString(msg))
}

@SuppressLint("Recycle")
fun Context.getDefaultToolbarHeight(): Int {
    val actionbarSizeTypedArray: TypedArray =
        obtainStyledAttributes(intArrayOf(R.attr.actionBarSize))
    return actionbarSizeTypedArray.getDimension(0, 0f).toInt()
}

fun Context.getDefaultStatusAndToolbarHeight(): Int {
    return getDefaultStatusHeight() + getDefaultToolbarHeight()
}