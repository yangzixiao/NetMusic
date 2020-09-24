package com.yzx.module_common.play

import com.yzx.lib_base.http.RetrofitProvider
import com.yzx.module_common.di.ModuleCommonServiceProvider
import com.yzx.module_common.model.MusicUrlResponse

class PlayRepository(private val provider: ModuleCommonServiceProvider) {

    suspend fun getMusicUrl(id: Long?): MusicUrlResponse {
        return provider.getCommonModuleService().getMusicUrl(id)
    }

}