package com.yzx.module_mine.api

import com.yzx.module_mine.model.net.MinePagerRecommendPlayListResponse
import com.yzx.module_mine.model.net.PersonalFMResponse
import com.yzx.module_mine.model.net.PlayListResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author yzx
 * @date 2020/7/17
 * Description
 */
interface MineApi {

    /**
     * 获取个人歌单
     */
    @GET("user/playlist")
    suspend fun getUserPlayLists(@Query("uid") uid: String,
        @Query("limit") limit: Int): PlayListResponse


    /**
     * 获取播放记录
     */
    @GET("user/record")
    suspend fun getUserPlayRecord(@Query("uid") uid: String): PlayListResponse

    /**
     * 获取私人FM歌单
     */
    @GET("personal_fm")
    suspend fun getPersonalFM(): PersonalFMResponse

    /**
     * 获取未登录推荐歌单
     */
    @GET("personalized")
    suspend fun getRecommendPlayLists(@Query("limit") limit: Int): MinePagerRecommendPlayListResponse
}