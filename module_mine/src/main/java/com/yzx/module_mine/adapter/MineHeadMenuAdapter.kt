package com.yzx.module_mine.adapter

import android.content.res.ColorStateList
import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.yzx.lib_core.ext.getColor
import com.yzx.module_mine.R
import com.yzx.module_mine.model.MineHeadMenuBean

class MineHeadMenuAdapter(data: MutableList<MineHeadMenuBean>? = null) :
    BaseQuickAdapter<MineHeadMenuBean, BaseViewHolder>(R.layout.item_mine_head_menu, data = data) {


    override fun convert(holder: BaseViewHolder, item: MineHeadMenuBean) {
            val ivIcon = holder.getView<ImageView>(R.id.ivIcon)
        ivIcon.setImageResource(item.icon)
        holder.setText(R.id.tvTitle, item.title)
        if (holder.layoutPosition==itemCount-1){
        }

        ivIcon.imageTintList=
            ColorStateList.valueOf(ivIcon.getColor(if (holder.layoutPosition==itemCount-1)R.color.colorGreyBackground else R.color.colorRed))
    }
}