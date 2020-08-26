package com.yzx.lib_base.ext

import android.widget.EditText

fun EditText.getContent():String{
    return this.text.toString().trim()
}