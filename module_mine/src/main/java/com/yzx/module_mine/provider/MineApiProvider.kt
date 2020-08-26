package com.yzx.module_mine.provider

import com.yzx.lib_base.http.RetrofitProvider
import com.yzx.module_mine.api.MineApi

class MineApiProvider(private val retrofitProvider: RetrofitProvider) {

    fun getMineApi():MineApi{
        return retrofitProvider.getRetrofit().create(MineApi::class.java)
    }
}