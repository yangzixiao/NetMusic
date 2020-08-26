package com.yzx.module_login.provider

import com.yzx.lib_base.http.RetrofitProvider
import com.yzx.module_login.api.LoginApi
import retrofit2.Retrofit

class LoginApiProvider(private val retrofitProvider: RetrofitProvider) {

    fun getLoginApi():LoginApi{
        return retrofitProvider.getRetrofit().create(LoginApi::class.java)
    }
}