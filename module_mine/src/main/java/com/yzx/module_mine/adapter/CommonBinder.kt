package com.yzx.module_mine.adapter

import com.multitype.adapter.binder.MultiTypeBinder
import com.yzx.lib_base.utils.DenistyUtils
import com.yzx.lib_base.widget.recyclerview.ExtraLinearItemDecoration
import com.yzx.module_mine.R
import com.yzx.module_mine.databinding.ItemTitleRecyclerviewBinding

abstract class CommonBinder(private var title: String) :
    MultiTypeBinder<ItemTitleRecyclerviewBinding>() {
    override fun layoutId(): Int = R.layout.item_title_recyclerview

    override fun onBindViewHolder(binding: ItemTitleRecyclerviewBinding) {
        super.onBindViewHolder(binding)
        binding.recyclerView.apply {
            isNestedScrollingEnabled = false
            addItemDecoration(ExtraLinearItemDecoration(DenistyUtils.dip2px(context,10f)))
        }

        binding.tvTitle.text = title
    }
}