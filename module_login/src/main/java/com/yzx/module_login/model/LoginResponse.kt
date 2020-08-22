package com.yzx.module_login.model

import com.yzx.lib_base.http.BaseResult

/**
 * @author yzx
 * @date 2020/5/13
 * Description
 */
data class LoginResponse(
    val adValid: Boolean,
    val bindings: List<Binding>,
    val createDays: Int,
    val createTime: Long,
    val level: Int,
    val listenSongs: Int,
    val mobileSign: Boolean,
    val pcSign: Boolean,
    val peopleCanSeeMyPlayRecord: Boolean,
    val profile: Profile,
    val userPoint: UserPoint
):BaseResult()

data class Binding(
    val bindingTime: Long,
    val expired: Boolean,
    val expiresIn: Int,
    val id: Long,
    val refreshTime: Int,
    val tokenJsonStr: Any,
    val type: Int,
    val url: String,
    val userId: Int
)



class Experts(
)

data class UserPoint(
    val balance: Int,
    val blockBalance: Int,
    val status: Int,
    val updateTime: Long,
    val userId: Int,
    val version: Int
)