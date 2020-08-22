package com.yzx.module_mine.model.net

import com.yzx.lib_base.http.BaseResult

data class MinePagerRecommendPlayListResponse(
    val category: Int,
    val hasTaste: Boolean,
    val result: List<Result>
):BaseResult()

data class Result(
    val alg: String,
    val canDislike: Boolean,
    val copywriter: String,
    val highQuality: Boolean,
    val id: Long,
    val name: String,
    val picUrl: String,
    val playCount: Int,
    val trackCount: Int,
    val trackNumberUpdateTime: Long,
    val type: Int
)