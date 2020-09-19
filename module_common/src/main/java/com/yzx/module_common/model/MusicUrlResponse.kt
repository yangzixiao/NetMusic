package com.yzx.module_common.model

import com.yzx.lib_base.http.BaseResult

data class MusicUrlResponse(
    val `data`: List<MusicUrlInfo>
):BaseResult()

data class MusicUrlInfo(
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
    val uf: Any,
    val url: String
)