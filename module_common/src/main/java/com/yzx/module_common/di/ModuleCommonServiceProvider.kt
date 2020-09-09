package com.yzx.module_common.di

import com.yzx.lib_base.http.RetrofitProvider
import com.yzx.module_common.service.CommonService

class ModuleCommonServiceProvider(private val retrofitProvider: RetrofitProvider) {

    fun getCommonModuleService():CommonService{
        return retrofitProvider.getRetrofit().create(CommonService::class.java)
    }
}