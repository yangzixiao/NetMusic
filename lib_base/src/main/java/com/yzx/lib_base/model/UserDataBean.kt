package com.yzx.lib_base.model

data class UserDataBean(var isLoggedIn: Boolean = false, var uid: Long = 0,
    var nickName: String? = null, var backgroundUrl: String? = null, var avatarUrl: String? = null,
    var isTourist: Boolean = false)