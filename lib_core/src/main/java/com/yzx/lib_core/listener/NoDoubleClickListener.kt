package com.yzx.lib_core.listener

import android.view.View

abstract class NoDoubleClickListener: View.OnClickListener {
    companion object{
        const val MIN_CLICK_DELAY_TIME = 500L
    }

    private var lastClickTime: Long = 0

    override fun onClick(v: View) {
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
            lastClickTime = currentTime
            onNoDoubleClick(v)
        }
    }

    protected abstract fun onNoDoubleClick(v: View)
}