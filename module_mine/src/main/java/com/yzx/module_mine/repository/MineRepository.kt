package com.yzx.module_mine.repository

import com.yzx.module_mine.model.net.MinePagerRecommendPlayListResponse
import com.yzx.module_mine.model.net.PersonalFMResponse
import com.yzx.module_mine.model.net.PlayListResponse
import com.yzx.module_mine.provider.MineApiProvider

class MineRepository(private val mineApiProvider: MineApiProvider) {


    suspend fun getPersonalFM(): PersonalFMResponse {
        return mineApiProvider.getMineApi().getPersonalFM()
    }

    suspend fun getRecommendPlayLists(): MinePagerRecommendPlayListResponse {
        return mineApiProvider.getMineApi().getRecommendPlayLists(6)
    }

    suspend fun getUserPlayLists(uid: String): PlayListResponse {
        return mineApiProvider.getMineApi().getUserPlayLists(uid, 1000)
    }
}