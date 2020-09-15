package com.yzx.module_common.adpter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.yzx.module_common.R

class PlayAlbumMenuAdapter(private val icons: MutableList<Int>) :
    BaseQuickAdapter<Int, BaseViewHolder>(R.layout.item_menu_img, icons) {
    override fun convert(holder: BaseViewHolder, item: Int) {
        holder.setImageResource(R.id.ivIcon, item)
    }
}