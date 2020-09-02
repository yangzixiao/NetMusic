package com.yzx.module_mine.adapter

import android.annotation.SuppressLint
import androidx.recyclerview.widget.LinearLayoutManager
import com.multitype.adapter.binder.MultiTypeBinder
import com.multitype.adapter.createMultiTypeAdapter
import com.yzx.lib_base.manager.UserInfoManager
import com.yzx.lib_base.utils.glide.GlideUtils
import com.yzx.module_mine.R
import com.yzx.module_mine.databinding.ItemPlaylistBinding
import com.yzx.module_mine.databinding.ItemTitleRecyclerviewBinding
import com.yzx.module_mine.model.net.PlaylistBean
import com.yzx.module_mine.model.net.Result

class HorizontalPlayListBinder(
    title: String, private var playLists: List<ItemPlayListBinder>
) : CommonBinder(title) {
    override fun areContentsTheSame(other: Any): Boolean {
        return other is HorizontalPlayListBinder && other.playLists == playLists
    }

    override fun onBindViewHolder(binding: ItemTitleRecyclerviewBinding) {
        super.onBindViewHolder(binding)

        createMultiTypeAdapter(
            binding.recyclerView,
            LinearLayoutManager(binding.recyclerView.context)
        ).apply {
            notifyAdapterChanged(playLists)
        }
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

        //我的歌单数据
        if (playlistBean is PlaylistBean) {
            (playlistBean as PlaylistBean).apply {
                GlideUtils.loadImg(coverImgUrl, binding.ivSongSheetPost)
                binding.tvTitle.text = name
                binding.tvSubTitle.text =  if (creator.userId== UserInfoManager.userInfoLiveData.value!!.uid) "${trackCount}首" else "${trackCount}首 by ${creator.nickname}"
            }
        } else if (playlistBean is Result) {//推荐歌单数据
            (playlistBean as Result).apply {
                GlideUtils.loadImg(picUrl, binding.ivSongSheetPost)
                binding.tvTitle.text = name
                binding.tvSubTitle.text = "推荐歌单"
            }
        }


    }

}