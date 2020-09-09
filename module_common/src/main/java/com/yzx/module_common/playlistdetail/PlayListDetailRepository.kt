package com.yzx.module_common.playlistdetail

import com.yzx.module_common.di.ModuleCommonServiceProvider
import com.yzx.module_common.model.PlayListDetailResponse

class PlayListDetailRepository(private val provider: ModuleCommonServiceProvider) {

    suspend fun getPlayListDetail(id: Long): PlayListDetailResponse {
        return provider.getCommonModuleService().getPlayListDetail(id)
    }
}