package com.yzx.module_common.model

import com.yzx.lib_base.http.BaseResult

data class PlayListSongsResponse(
    val songs: List<Track>
): BaseResult()