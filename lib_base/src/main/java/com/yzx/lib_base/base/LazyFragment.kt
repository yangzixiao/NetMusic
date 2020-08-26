package com.yzx.lib_base.base

import androidx.fragment.app.Fragment

open class LazyFragment:Fragment() {

    private var isLoad = false

    override fun onResume() {
        super.onResume()
        if (!isLoad) {
            lazyLoad()
            isLoad = true
        }
    }

    /**
     * 懒加载
     */
    open fun lazyLoad() {

    }
}