package com.yzx.module_login.repository

import com.yzx.module_login.model.LoginResponse
import com.yzx.module_login.provider.LoginApiProvider

class LoginRepository(private val loginApiProvider: LoginApiProvider) {

    suspend fun login(phone: String, password: String): LoginResponse {
        return loginApiProvider.getLoginApi().login(phone, password)
    }
}