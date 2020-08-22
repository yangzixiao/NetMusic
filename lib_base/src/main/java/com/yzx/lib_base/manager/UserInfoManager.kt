package com.yzx.lib_base.manager

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yzx.lib_base.model.UserDataBean

/**
 * @author yzx
 * @date 2020/5/13
 * Description 用户数据管理
 */
object UserInfoManager {

    /**
     * 用户数据
     */
    var userInfoLiveData: MutableLiveData<UserDataBean> = MutableLiveData()
    var isTourist: Boolean = false

    fun reset() {
        userInfoLiveData.value = UserDataBean()
    }
}