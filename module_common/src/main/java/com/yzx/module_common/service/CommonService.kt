package com.yzx.module_common.service

import com.yzx.module_common.model.MusicUrlResponse
import com.yzx.module_common.model.PlayListDetailResponse
import com.yzx.module_common.model.PlayListSongsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CommonService {


    @GET("playlist/detail")
    suspend fun getPlayListDetail(@Query("id") id: Long): PlayListDetailResponse


    @GET("song/detail")
    suspend fun getPlayListDetail(@Query("ids") id: String): PlayListSongsResponse

    /**
     * 歌曲Url
     */
    @GET("song/url")
    suspend fun getMusicUrl(@Query("id") id: Long?): MusicUrlResponse
}