package com.yzx.module_common.service

import com.yzx.module_common.model.PlayListDetailResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CommonService {


    @GET("playlist/detail")
    suspend fun getPlayListDetail(@Query("id") id: Long): PlayListDetailResponse
}