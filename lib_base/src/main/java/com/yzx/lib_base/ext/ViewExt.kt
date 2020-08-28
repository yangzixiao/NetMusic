package com.yzx.lib_base.ext

import android.view.View
import android.widget.EditText

/**
 * 获取EdiText内容
 */
fun EditText.getContent(): String {
    return this.text.toString().trim()
}
/**
 * 控制View可见
 */
fun View.visible() {
    visibility = View.VISIBLE
}

/**
 * 控制View可见
 */
fun View.invisible() {
    visibility = View.INVISIBLE
}

/**
 * 控制View可见
 */
fun View.gone() {
    visibility = View.GONE
}