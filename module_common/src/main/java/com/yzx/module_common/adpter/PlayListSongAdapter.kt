package com.yzx.module_common.adpter

import android.widget.ImageView
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.yzx.lib_base.arouter.ARouterNavUtils
import com.yzx.lib_core.ext.gone
import com.yzx.lib_core.ext.visible
import com.yzx.module_common.MusicDataCovert
import com.yzx.module_common.R
import com.yzx.module_common.model.PlayListSinger
import com.yzx.module_common.model.Track


class PlayListSongAdapter(songs: MutableList<Track>? = null) :
    BaseQuickAdapter<Track, BaseViewHolder>(R.layout.item_song, data = songs) {

    private var playListId = 0L
    private val stringBuilder = StringBuilder()
    override fun convert(holder: BaseViewHolder, item: Track) {

        holder.setText(R.id.tvIndex, (holder.layoutPosition + 1).toString())
        holder.setText(R.id.tvSongName, item.name)
        holder.setText(R.id.tvSongSinger, getSingers(item.ar, item.al.name))

        if (item.url.isNullOrEmpty()) {
            holder.getView<TextView>(R.id.tvSongName).alpha = 0.6f
            holder.getView<TextView>(R.id.tvSongSinger).alpha = 0.6f
            holder.itemView.isClickable = false
        } else {
            holder.getView<TextView>(R.id.tvSongName).alpha = 1f
            holder.getView<TextView>(R.id.tvSongSinger).alpha = 1f
            holder.itemView.isClickable = true
        }

        val ivMV = holder.getView<ImageView>(R.id.ivMV)
        if (item.mv == 0L) {
            ivMV.gone()
        } else {
            ivMV.visible()
        }

        holder.itemView.setOnClickListener {
            MusicDataCovert.coverPlayList(playListId, data, holder.layoutPosition)
        }
    }

    fun setPlayListId(newPlayListId: Long) {
        playListId = newPlayListId
    }

    private fun getSingers(ar: List<PlayListSinger>, albumName: String): String {
        if (ar.isNullOrEmpty()) {
            return ""
        }
        if (ar.size == 1) {
            return ar[0].name
        }

        stringBuilder.clear()
        ar.forEachIndexed { index, a ->
            stringBuilder.append(a.name)
            if (index != ar.lastIndex) {
                stringBuilder.append("/")
            } else {
                stringBuilder.append(" - $albumName")
            }

        }
        return stringBuilder.toString()
    }
}