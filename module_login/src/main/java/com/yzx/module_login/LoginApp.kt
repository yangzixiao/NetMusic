package com.yzx.module_login

import android.app.Application
import com.yzx.lib_base.app.BaseApplication
import com.yzx.lib_base.mmkv.MmkvUtils
import com.yzx.lib_base.model.UserDataBean
import com.yzx.lib_base.utils.JsonUtils
import com.yzx.lib_base.utils.LogUtils
import com.yzx.module_login.model.LoginResponse

/**
 * @author yzx
 * @date 2020/5/13
 * Description LoginApp
 */
class LoginApp : BaseApplication() {

    companion object {
        const val TAG = "LoginApp"
    }

    override fun initModuleApp(application: Application?) {
        LogUtils.e(TAG, "initModuleApp")
    }

    override fun initModuleData(application: Application?) {
        LogUtils.e(TAG, "initModuleData")

        /**
         * 初始化用户数据
         */
        val loginResponseString = MmkvUtils.get(Constants.KEY_LOGIN_RESPONSE, "")
        val isTourist = MmkvUtils.get(Constants.KEY_IS_TOURIST, false)
        if (loginResponseString.isNotEmpty()) {
            val loginResponse = JsonUtils.stringToObject(loginResponseString,
                LoginResponse::class.java) as LoginResponse
            UserDataUtils.initUserInfo(loginResponse)
            UserDataUtils.updateTouristState(isTourist)
            LogUtils.e(TAG, "initModuleData用户数据初始化完成$loginResponse")
        } else {
            LogUtils.e(TAG, "initModuleData用户未登录")

        }
    }
}