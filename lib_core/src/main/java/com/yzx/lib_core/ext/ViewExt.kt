package com.yzx.lib_core.ext

import android.view.View
import android.widget.EditText
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import com.yzx.lib_core.listener.NoDoubleClickListener

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

fun View.getColor(@ColorRes colorRes: Int): Int {
    return if (context == null) 0x00000000
    else ContextCompat.getColor(context!!, colorRes)
}

/**
 * 为 View 添加方法，以后使用这个方法来替换 setOnClickListener 就可以了
 */
fun View.onDebouncedClick(click: (view: View) -> Unit) {
    setOnClickListener(object : NoDoubleClickListener() {
        override fun onNoDoubleClick(v: View) {
            click(v)
        }
    })
}