package com.yzx.module_mine.model.section

import com.chad.library.adapter.base.entity.SectionEntity

class MinePlayListSection(private var isHeadSection: Boolean, val data: Any, var isLast: Boolean = false) : SectionEntity {
    override val isHeader: Boolean
        get() = isHeadSection
}