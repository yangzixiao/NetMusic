package com.yzx.module_mine.adapter

import androidx.core.view.ViewCompat
import com.multitype.adapter.binder.MultiTypeBinder
import com.yzx.module_mine.R
import com.yzx.module_mine.databinding.ItemTitleRecyclerviewBinding

abstract class CommonBinder(var titles: List<String>) :
    MultiTypeBinder<ItemTitleRecyclerviewBinding>() {
    override fun layoutId(): Int = R.layout.item_title_recyclerview

    override fun onBindViewHolder(binding: ItemTitleRecyclerviewBinding) {
        super.onBindViewHolder(binding)
        binding.recyclerView.isNestedScrollingEnabled = false
        binding.titleTabLayout.apply {
            removeAllTabs()
            titles.forEach {
                val newTab = newTab()

                addTab(newTab.setText(it))
                val customView = newTab.view
                customView.setBackgroundResource(R.color.colorTransparent)
                ViewCompat.setPaddingRelative(customView, 0, customView.paddingTop,
                    customView.paddingEnd, customView.paddingBottom)
            }
        }
    }
}