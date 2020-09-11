package com.yzx.lib_base.ext


import android.graphics.drawable.Drawable
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat


fun AppCompatActivity.toast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

fun AppCompatActivity.toast(@StringRes msg: Int) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}


fun AppCompatActivity.simpleGetString(@StringRes vararg stringRes: Int): String {
    var result = ""
    stringRes.forEach {
        result += getString(it)
    }
    return result
}

fun AppCompatActivity.simpleGetDrawable(@DrawableRes  drawableRes: Int): Drawable {
    return ContextCompat.getDrawable(this,drawableRes)!!
}
