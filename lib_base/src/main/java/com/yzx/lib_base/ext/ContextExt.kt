package com.yzx.lib_base.ext

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.widget.Toast
import androidx.annotation.StringRes
import com.yzx.lib_base.R


fun Context.toast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

fun Context.toast(@StringRes msg: Int) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

@SuppressLint("Recycle")
fun Context.getToolBarSize(): Int {


    val actionbarSizeTypedArray: TypedArray =
        obtainStyledAttributes(intArrayOf(R.attr.actionBarSize))
//    actionbarSizeTypedArray.recycle()
    return actionbarSizeTypedArray.getDimension(0, 0f).toInt()
}