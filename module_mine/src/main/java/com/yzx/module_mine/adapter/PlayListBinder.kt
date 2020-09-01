package com.yzx.module_mine.adapter

import android.annotation.SuppressLint
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.tabs.TabLayout
import com.multitype.adapter.binder.MultiTypeBinder
import com.multitype.adapter.createMultiTypeAdapter
import com.yzx.lib_base.arouter.ARouterNavUtils
import com.yzx.lib_base.arouter.ARouterPath
import com.yzx.lib_base.utils.glide.GlideUtils
import com.yzx.module_mine.R
import com.yzx.module_mine.databinding.ItemPlaylistBinding
import com.yzx.module_mine.databinding.ItemTitleRecyclerviewBinding
import com.yzx.module_mine.model.net.PlaylistBean
import com.yzx.module_mine.model.net.Result

class PlayListBinder(titles: List<String>, var data0: List<ItemPlayListBinder>,
    var data1: List<ItemPlayListBinder> = listOf()) : CommonBinder(titles) {
    override fun areContentsTheSame(other: Any): Boolean {
        return other is PlayListBinder && other.data0 == data0 && other.data1 == data1
    }

    override fun onBindViewHolder(binding: ItemTitleRecyclerviewBinding) {
        super.onBindViewHolder(binding)
        val playListAdapter = createMultiTypeAdapter(binding.recyclerView,
            GridLayoutManager(binding.recyclerView.context, 2))

        playListAdapter.notifyAdapterChanged(data0)
        binding.titleTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (binding.titleTabLayout.selectedTabPosition) {
                    0 -> playListAdapter.notifyAdapterChanged(data0)
                    1 -> playListAdapter.notifyAdapterChanged(data1)
                    else -> playListAdapter.notifyAdapterChanged(listOf())
                }
            }
        })
    }
}


class ItemPlayListBinder(var playlistBean: Any) : MultiTypeBinder<ItemPlaylistBinding>() {
    override fun layoutId(): Int = R.layout.item_playlist

    override fun areContentsTheSame(other: Any): Boolean {
        return other is ItemPlayListBinder && playlistBean == other.playlistBean
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(binding: ItemPlaylistBinding) {
        super.onBindViewHolder(binding)

        if (playlistBean is PlaylistBean) {
            (playlistBean as PlaylistBean).apply {

                GlideUtils.loadImg(coverImgUrl, binding.ivSongSheetPost)
                binding.tvTitle.text = name
                binding.tvSubTitle.text = "${trackCount}首"
            }
        } else if (playlistBean is Result) {
            (playlistBean as Result).apply {
                GlideUtils.loadImg(picUrl, binding.ivSongSheetPost)
                binding.tvTitle.text = name
                binding.tvSubTitle.text = "推荐歌单"
            }
        }

        binding.root.setOnClickListener {
            ARouterNavUtils.normalNav(ARouterPath.COMMON_PLAYLIST_DETAIL)
        }


    }

}