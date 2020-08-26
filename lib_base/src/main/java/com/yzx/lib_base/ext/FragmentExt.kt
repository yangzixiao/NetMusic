package com.yzx.lib_base.ext

import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

fun Fragment.toast(msg: String) {
    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
}

fun Fragment.toast(@StringRes msg: Int) {
    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
}