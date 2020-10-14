package com.yzx.module_mine.model.section

import com.chad.library.adapter.base.entity.SectionEntity

/**
 * @isHeadSection 是否是head
 * @data 数据
 * @isLast content是否是最后一条
 * @type head类型
 */
class MinePlayListSection(private var isHeadSection: Boolean, val data: Any, var isLast: Boolean = false, var type: Int = TYPE_DEFAULT) :
    SectionEntity {
    companion object {
        const val TYPE_DEFAULT = 0
        const val TYPE_CREATE = 1
        const val TYPE_COLLECTION = 2
        const val TYPE_RECOMMEND = 3
    }

    override val isHeader: Boolean
        get() = isHeadSection
}