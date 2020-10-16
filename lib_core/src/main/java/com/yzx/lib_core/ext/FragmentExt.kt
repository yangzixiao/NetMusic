package com.yzx.lib_core.ext

import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

fun Fragment.toast(msg: String) {
    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
}

fun Fragment.toast(@StringRes msg: Int) {
    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
}