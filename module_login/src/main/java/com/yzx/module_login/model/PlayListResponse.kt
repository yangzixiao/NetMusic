package com.yzx.module_login.model

/**
 * @author yzx
 * @date 2020/5/13
 * Description
 */
data class PlayListResponse(
    val code: Int,
    val more: Boolean,
    val playlist: List<Playlist>
)

data class Playlist(
    val adType: Int,
    val anonimous: Boolean,
    val artists: Any,
    val backgroundCoverId: Long,
    val backgroundCoverUrl: Any,
    val cloudTrackCount: Int,
    val commentThreadId: String,
    val coverImgId: Long,
    val coverImgId_str: String,
    val coverImgUrl: String,
    val createTime: Long,
    val creator: Creator,
    val description: String,
    val englishTitle: Any,
    val highQuality: Boolean,
    val id: Int,
    val name: String,
    val newImported: Boolean,
    val opRecommend: Boolean,
    val ordered: Boolean,
    val playCount: Int,
    val privacy: Int,
    val recommendInfo: Any,
    val specialType: Int,
    val status: Int,
    val subscribed: Boolean,
    val subscribedCount: Int,
    val subscribers: List<Any>,
    val tags: List<Any>,
    val titleImage: Int,
    val titleImageUrl: Any,
    val totalDuration: Int,
    val trackCount: Int,
    val trackNumberUpdateTime: Long,
    val trackUpdateTime: Long,
    val tracks: Any,
    val updateFrequency: Any,
    val updateTime: Long,
    val userId: Int
)

data class Creator(
    val accountStatus: Int,
    val authStatus: Int,
    val authority: Int,
    val avatarImgId: Long,
    val avatarImgIdStr: String,
    val avatarUrl: String,
    val backgroundImgId: Long,
    val backgroundImgIdStr: String,
    val backgroundUrl: String,
    val birthday: Long,
    val city: Int,
    val defaultAvatar: Boolean,
    val description: String,
    val detailDescription: String,
    val djStatus: Int,
    val expertTags: Any,
    val experts: Any,
    val followed: Boolean,
    val gender: Int,
    val mutual: Boolean,
    val nickname: String,
    val province: Int,
    val remarkName: Any,
    val signature: String,
    val userId: Int,
    val userType: Int,
    val vipType: Int
)