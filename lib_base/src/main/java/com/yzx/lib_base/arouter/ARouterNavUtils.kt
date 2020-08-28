package com.yzx.lib_base.arouter

import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.launcher.ARouter

/**
 * @author yzx
 * @date 2020/5/13
 * Description
 */
object ARouterNavUtils {


    /**
     * 跳转
     */
    fun normalNav(path: String) {
        ARouter.getInstance().build(path).navigation()
    }

    /**
     * 跳转
     */
    fun getPostcard(path: String): Postcard {
       return ARouter.getInstance().build(path)
    }


    /**
     * 根据路径获取Fragment
     */
    fun getFragment(path: String) :Fragment{

        return ARouter.getInstance().build(path).navigation() as Fragment
    }
}