package com.yzx.module_mine.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.yzx.module_mine.R
import com.yzx.module_mine.model.MineHeadMenuBean

class MineHeadMenuAdapter(data: MutableList<MineHeadMenuBean>? = null) :
    BaseQuickAdapter<MineHeadMenuBean, BaseViewHolder>(R.layout.item_mine_head_menu, data = data) {


    override fun convert(holder: BaseViewHolder, item: MineHeadMenuBean) {
        holder.setImageResource(R.id.ivIcon, item.icon)
        holder.setText(R.id.tvTitle, item.title)
    }
}