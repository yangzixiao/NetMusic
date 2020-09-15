package com.yzx.module_common.adpter

import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.yzx.lib_base.ext.toast
import com.yzx.module_common.R

class ImgMenuAdapter(private val icons: MutableList<Int>) :
    BaseQuickAdapter<Int, BaseViewHolder>(R.layout.item_menu_img, icons) {
    override fun convert(holder: BaseViewHolder, item: Int) {
        holder.setImageResource(R.id.ivIcon, item)
    }

    fun changeIcon(position: Int, @DrawableRes drawableRes: Int, badge: Int = 0) {
        recyclerView.getChildAt(position).apply {
            findViewById<ImageView>(R.id.ivIcon).setImageResource(drawableRes)
            findViewById<TextView>(R.id.tvBadge).text = if (badge == 0) "" else badge.toString()
        }
    }
}