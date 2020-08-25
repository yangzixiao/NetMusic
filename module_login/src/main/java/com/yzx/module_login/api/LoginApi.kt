package com.yzx.module_login.api

import com.yzx.module_login.model.LoginResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author yzx
 * @date 2020/5/13
 * Description
 */
interface LoginApi {

    /**
     * 手机号登录
     */
    @GET("login/cellphone")
    suspend fun login(
        @Query("phone") phone: String,
        @Query("password") password: String
    ): LoginResponse

}