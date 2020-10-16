package com.yzx.module_login

import com.yzx.lib_base.manager.UserInfoManager
import com.yzx.lib_core.mmkv.MmkvUtils
import com.yzx.lib_base.model.UserDataBean
import com.yzx.lib_core.utils.JsonUtils
import com.yzx.module_login.model.LoginResponse

/**
 * @author yzx
 * @date 2020/5/14
 * Description
 */
object UserDataUtils {

    private var userDataBean = UserDataBean()
    fun initUserInfo(loginResponse: LoginResponse? = null) {

        if (loginResponse == null) {
            UserInfoManager.userInfoLiveData.value = userDataBean
            return
        }else{
            loginResponse.profile.apply {
                userDataBean.isLoggedIn = true
                userDataBean.uid = userId
                userDataBean.nickName = nickname
                userDataBean.avatarUrl = avatarUrl
                userDataBean.backgroundUrl = backgroundUrl
            }
            UserInfoManager.userInfoLiveData.value = userDataBean
            MmkvUtils.put(Constants.KEY_LOGIN_RESPONSE, JsonUtils.objectToString(loginResponse))
        }

    }

    fun updateTouristState(isTourist: Boolean) {
        UserInfoManager.isTourist = isTourist
        MmkvUtils.put(Constants.KEY_IS_TOURIST, isTourist)
    }


}