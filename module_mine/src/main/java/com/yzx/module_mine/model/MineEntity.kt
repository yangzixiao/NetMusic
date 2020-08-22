package com.yzx.module_mine.model

import com.chad.library.adapter.base.entity.SectionEntity
import com.yzx.module_mine.model.net.PlaylistBean

/**
 * @author yzx
 * @date 2020/7/22
 * Description
 */
data class MineEntity(
    var isHead: Boolean = false,
    var title: List<String>? = null,
    var playListBean: PlaylistBean? = null
) : SectionEntity {
    override val isHeader: Boolean
        get() = isHead
}