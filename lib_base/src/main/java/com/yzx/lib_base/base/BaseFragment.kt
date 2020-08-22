package com.yzx.lib_base.base

/**
 * @author yzx
 * @date 2020/7/18
 * Description 懒加载
 */
open class BaseFragment<VM : BaseViewModel> : UIFragment<VM>() {

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