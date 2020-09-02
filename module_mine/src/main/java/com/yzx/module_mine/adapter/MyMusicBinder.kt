package com.yzx.module_mine.adapter

import android.content.res.ColorStateList
import android.graphics.Color
import androidx.recyclerview.widget.LinearLayoutManager
import com.multitype.adapter.binder.MultiTypeBinder
import com.multitype.adapter.createMultiTypeAdapter
import com.yzx.lib_base.ext.visible
import com.yzx.lib_base.utils.ColorUtils.getColorByAlpha
import com.yzx.lib_base.utils.DenistyUtils.dip2px
import com.yzx.lib_base.utils.glide.GlideUtils
import com.yzx.lib_base.widget.recyclerview.ExtraLinearItemDecoration
import com.yzx.module_mine.R
import com.yzx.module_mine.databinding.ItemChildMyMusicBinding
import com.yzx.module_mine.databinding.ItemTitleRecyclerviewBinding
import com.yzx.module_mine.model.MyMusicBean

class MyMusicBinder(title: String, var data: List<MyMusicItemBinder>) :
    CommonBinder(title) {
    override fun areContentsTheSame(other: Any): Boolean {
        return true
    }

    override fun onBindViewHolder(binding: ItemTitleRecyclerviewBinding) {
        super.onBindViewHolder(binding)

        val context = binding.recyclerView.context
        binding.recyclerView.addItemDecoration(
            ExtraLinearItemDecoration(dip2px(context, 16f), dip2px(context, 8f))
        )
        createMultiTypeAdapter(
            binding.recyclerView,
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        ).apply {
            notifyAdapterChanged(data)
        }
    }
}

class MyMusicItemBinder(var myMusicBean: MyMusicBean) : MultiTypeBinder<ItemChildMyMusicBinding>() {
    override fun layoutId(): Int = R.layout.item_child_my_music

    override fun areContentsTheSame(other: Any): Boolean {
        return other is MyMusicItemBinder && myMusicBean.background == other.myMusicBean.background && myMusicBean.keyColor == other.myMusicBean.keyColor
    }

    override fun onBindViewHolder(binding: ItemChildMyMusicBinding) {
        super.onBindViewHolder(binding)


        binding.apply {
            tvTitle.text = myMusicBean.title
            tvDes.text = myMusicBean.des
            GlideUtils.simpleLoadImg(myMusicBean.icon, ivIcon)
            if (myMusicBean.background != null) {
                tvRecommend.visible()
                GlideUtils.loadImg(myMusicBean.background, ivBackground)
                ivBackground.setMaskColor(getColorByAlpha(0x212121, 0.5f))
                val colorWhite = Color.WHITE
                tvTitle.setTextColor(colorWhite)
                val colorLightColor = getColorByAlpha(colorWhite, 0.5f)
                tvRecommend.setTextColor(colorLightColor)
                tvDes.setTextColor(colorLightColor)
            } else {
                tvRecommend.visible()
                val color = myMusicBean.keyColor
                tvTitle.setTextColor(color)
                val colorByAlpha = getColorByAlpha(color, 0.33f)
                tvDes.setTextColor(colorByAlpha)
                tvRecommend.setTextColor(colorByAlpha)
                ivBackground.setMaskColor(getColorByAlpha(color, 0.2f))
                ivIcon.imageTintList = ColorStateList.valueOf(color)
            }
        }
    }

}