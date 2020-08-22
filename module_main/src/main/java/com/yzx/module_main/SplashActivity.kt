package com.yzx.module_main

import android.os.Bundle
import com.yzx.lib_base.ARouter.ARouterNavUtils
import com.yzx.lib_base.ARouter.ARouterPath
import com.yzx.lib_base.CommonVIewModel
import com.yzx.lib_base.base.BaseActivity
import com.yzx.lib_base.manager.UserInfoManager
import com.yzx.module_main.databinding.ActivitySplashBinding

/**
 * @author yzx
 * @date 2020/5/11
 * Description 闪屏
 */
class SplashActivity : BaseActivity<CommonVIewModel>() {


    override fun getActivityEnterAnim(): Int {
        return R.anim.slide_in_scale
    }

    override fun getActivityExitAnim(): Int {
        return R.anim.slide_out_scale
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val splashBinding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(splashBinding.root)
        splashBinding.layoutContent.setBackgroundResource(R.color.colorRed)

        setFullScreen()

        splashBinding.root.postDelayed({
            ARouterNavUtils.normalNav(if (UserInfoManager.userInfoLiveData.value!!.isLoggedIn or UserInfoManager.isTourist) ARouterPath.MAIN else ARouterPath.LOGIN_GUIDE)
            finish()
        }, 1500)
    }
}