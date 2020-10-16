package com.yzx.module_main

import android.Manifest
import android.os.Bundle
import com.permissionx.guolindev.PermissionX
import com.yzx.lib_base.arouter.ARouterNavUtils
import com.yzx.lib_base.arouter.ARouterPath
import com.yzx.lib_base.base.BaseActivity
import com.yzx.lib_core.ext.toast
import com.yzx.lib_base.manager.UserInfoManager
import com.yzx.module_main.databinding.ActivitySplashBinding
import org.jetbrains.annotations.NotNull

/**
 * @author yzx
 * @date 2020/5/11
 * Description 闪屏
 */
class SplashActivity : BaseActivity() {


    private var startTime: Long = 0
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

        startTime = System.currentTimeMillis()
        PermissionX.init(this).permissions(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE)
            .explainReasonBeforeRequest()
            .onExplainRequestReason { scope, deniedList ->
                scope.showRequestReasonDialog(deniedList, "即将申请的权限是程序必须依赖的权限", "我已明白")
            }
            .onForwardToSettings { scope, deniedList ->
                scope.showForwardToSettingsDialog(deniedList, "您需要去应用程序设置当中手动开启权限", "我已明白")
            }
            .request { allGranted, grantedList, deniedList ->
                if (allGranted) {
                    toNextPager(splashBinding)
                } else {
                    toast("您拒绝了必要权限")
                    finish()
                }
            }

    }

    private fun toNextPager(splashBinding: @NotNull ActivitySplashBinding) {
        splashBinding.root.postDelayed({
            ARouterNavUtils.normalNav(
                if (UserInfoManager.userInfoLiveData.value!!.isLoggedIn or UserInfoManager.isTourist) ARouterPath.MAIN else ARouterPath.LOGIN_GUIDE)
            finish()
        }, if ((System.currentTimeMillis() - startTime) > 1500) 0 else 1500)
    }
}