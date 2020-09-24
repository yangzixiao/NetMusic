package com.yzx.module_common.model

import com.yzx.lib_base.http.BaseResult

data class PlayListUrlsResponse(
    val `data`: List<SongUrlData>
):BaseResult()

data class SongUrlData(
    val br: Long,
    val canExtend: Boolean,
    val code: Int,
    val encodeType: String,
    val expi: Int,
    val fee: Int,
    val flag: Int,
    val freeTrialInfo: Any,
    val gain: Int,
    val id: Long,
    val level: String,
    val md5: String,
    val payed: Int,
    val size: Long,
    val type: String,
    val url: String
)