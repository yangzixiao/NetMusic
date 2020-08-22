package com.yzx.module_mine.model.net

import com.yzx.lib_base.http.BaseResult

data class PersonalFMResponse(

    val `data`: List<PersonalFMBean>,
    val popAdjust: Boolean
) : BaseResult()

data class PersonalFMBean(
    val album: Album

)

data class Album(

    val blurPicUrl: String,
    val briefDesc: String,
    val commentThreadId: String,
    val company: String,

    val description: String,
    val id: Double,

    val name: String,
    val pic: Long,
    val picId: Long,
    val picId_str: String,
    val picUrl: String,
    val publishTime: Long
)

data class ArtistXX(
    val albumSize: Int,
    val alias: List<Any>,
    val briefDesc: String,
    val id: Int,
    val img1v1Id: Int,
    val img1v1Url: String,
    val musicSize: Int,
    val name: String,
    val picId: Int,
    val picUrl: String,
    val topicPerson: Int,
    val trans: String
)

data class BMusic(
    val bitrate: Int,
    val dfsId: Int,
    val extension: String,
    val id: Long,
    val name: Any,
    val playTime: Int,
    val size: Int,
    val sr: Int,
    val volumeDelta: Int
)

data class HMusic(
    val bitrate: Int,
    val dfsId: Int,
    val extension: String,
    val id: Long,
    val name: Any,
    val playTime: Int,
    val size: Int,
    val sr: Int,
    val volumeDelta: Int
)

data class LMusic(
    val bitrate: Int,
    val dfsId: Int,
    val extension: String,
    val id: Long,
    val name: Any,
    val playTime: Int,
    val size: Int,
    val sr: Int,
    val volumeDelta: Int
)

data class MMusic(
    val bitrate: Int,
    val dfsId: Int,
    val extension: String,
    val id: Long,
    val name: Any,
    val playTime: Int,
    val size: Int,
    val sr: Int,
    val volumeDelta: Int
)

data class Privilege(
    val cp: Int,
    val cs: Boolean,
    val dl: Int,
    val fee: Int,
    val fl: Int,
    val flag: Int,
    val id: Int,
    val maxbr: Int,
    val payed: Int,
    val pl: Int,
    val preSell: Boolean,
    val sp: Int,
    val st: Int,
    val subp: Int,
    val toast: Boolean
)

data class Artist(
    val albumSize: Int,
    val alias: List<Any>,
    val briefDesc: String,
    val id: Int,
    val img1v1Id: Int,
    val img1v1Url: String,
    val musicSize: Int,
    val name: String,
    val picId: Int,
    val picUrl: String,
    val topicPerson: Int,
    val trans: String
)

data class ArtistX(
    val albumSize: Int,
    val alias: List<Any>,
    val briefDesc: String,
    val id: Int,
    val img1v1Id: Int,
    val img1v1Url: String,
    val musicSize: Int,
    val name: String,
    val picId: Int,
    val picUrl: String,
    val topicPerson: Int,
    val trans: String
)