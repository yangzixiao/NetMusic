package com.yzx.lib_core.ext

import android.util.Log
import com.yzx.lib_core.BuildConfig

const val TAG = "ygl"


fun i(msg: String) {
    i(tag = "", msg)
}

fun i(tag: String, msg: String) {
    if (BuildConfig.DEBUG) {
        Log.i(TAG + tag, msg)
    }
}

fun e(msg: String) {
    e(tag = "", msg)
}

fun e(tag: String, msg: String) {
    if (BuildConfig.DEBUG) {
        Log.e(TAG + tag, msg)
    }
}