package com.yzx.module_common.playlistdetail

import com.yzx.module_common.di.ModuleCommonServiceProvider
import com.yzx.module_common.model.PlayListDetailResponse
import com.yzx.module_common.model.PlayListSongsResponse
import com.yzx.module_common.model.PlayListUrlsResponse

class PlayListDetailRepository(private val provider: ModuleCommonServiceProvider) {

    suspend fun getPlayListDetail(id: Long): PlayListDetailResponse {
        return provider.getCommonModuleService().getPlayListDetail(id)
    }

    suspend fun getPlayListDetail(ids: String): PlayListSongsResponse {
        return provider.getCommonModuleService().getPlayListDetail(ids)
    }

   suspend fun getPlayListUrls(ids: String) :PlayListUrlsResponse{
       return provider.getCommonModuleService().getPlayListUrls(ids)
    }
}